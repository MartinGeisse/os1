package name.martingeisse.os.core;

import name.martingeisse.os.core.message.ClientRequestCycle;
import name.martingeisse.os.core.message.Request;
import name.martingeisse.os.core.message.ServerRequestCycle;
import name.martingeisse.os.core.message.dispatcher.NullDispatcher;
import name.martingeisse.os.core.message.dispatcher.RequestDispatcher;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Process {

    private final Process parent;
    private final Code code;
    private final RequestDispatcher requestDispatcher;
    private boolean started = false;
    private ProcessContext context;
    private boolean finished = false;
    private final BlockingQueue<ServerRequestCycle> incomingRequestQueue = new LinkedBlockingQueue<>();

    Process(Process parent, Code code) {
        this(parent, code, NullDispatcher.INSTANCE);
    }

    Process(Process parent, Code code, RequestDispatcher requestDispatcher) {
        this.parent = parent;
        this.code = code;
        this.requestDispatcher = requestDispatcher;
    }

    public void start() {
        synchronized(this) {
            if (started) {
                throw new IllegalStateException("process already started");
            }
            started = true;
            context = new ProcessContextImpl();
        }
        new Thread(this::body).start();
    }

    private void body() {
        ProcessContext contextLocalVar;
        synchronized(this) {
            contextLocalVar = context;
        }
        try {
            code.run(contextLocalVar);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        synchronized(this) {
            context = null;
            finished = true;
        }
    }

    public Process getParent() {
        return parent;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized boolean isFinished() {
        return finished;
    }

    class ProcessContextImpl implements ProcessContext {

        @Override
        public ClientRequestCycle sendRequest(Request request) {
            if (parent == null) {
                throw new RuntimeException("no parent process");
            }
            ClientRequestCycle clientRequestCycle = new ClientRequestCycle();
            ServerRequestCycle serverRequestCycle = new ServerRequestCycle(request, clientRequestCycle);
            Process destination = requestDispatcher.dispatch(request, Process.this);
            destination.incomingRequestQueue.add(serverRequestCycle);
            return clientRequestCycle;
        }

        @Override
        public ServerRequestCycle receiveRequest() throws InterruptedException {
            return incomingRequestQueue.take();
        }

        @Override
        public ServerRequestCycle pollRequest() {
            return incomingRequestQueue.poll();
        }

        @Override
        public ServerRequestCycle pollRequest(long timeoutMilliseconds) throws InterruptedException {
            return incomingRequestQueue.poll(timeoutMilliseconds, TimeUnit.MILLISECONDS);
        }

        @Override
        public Process createUnstartedChildProcess(Code code) {
            return new Process(Process.this, code);
        }

        @Override
        public Process createUnstartedChildProcess(Code code, RequestDispatcher requestDispatcher) {
            return new Process(Process.this, code, requestDispatcher);
        }

        @Override
        public Process startChildProcess(Code code) {
            Process result = createUnstartedChildProcess(code);
            result.start();
            return result;
        }

        @Override
        public Process startChildProcess(Code code, RequestDispatcher requestDispatcher) {
            Process result = createUnstartedChildProcess(code, requestDispatcher);
            result.start();
            return result;
        }

    }
}
