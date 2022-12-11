package name.martingeisse.os.core.message.subscription;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.request.Response;
import name.martingeisse.os.core.message.request.ServerRequestCycle;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public final class ClientSubscriptionCycle {

    final BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<>();
    // not public API
    public final ConcurrentHashMap<ServerSubscriptionCycle, ServerSubscriptionCycle> serverSubscriptionCycles = new ConcurrentHashMap<>();
    private boolean cancelled = false;

    public Response receiveResponse() throws InterruptedException {
        return responseQueue.take();
    }

    public Response pollResponse() {
        return responseQueue.poll();
    }

    public Response pollResponse(long timeoutMilliseconds) throws InterruptedException {
        return responseQueue.poll(timeoutMilliseconds, TimeUnit.MILLISECONDS);
    }

    public void cancel() {
        if (cancelled) {
            throw new IllegalStateException("already cancelled");
        }
        cancelled = true;
        for (ServerSubscriptionCycle serverSubscriptionCycle : serverSubscriptionCycles.keySet()) {
            serverSubscriptionCycle.process.postSubscriptionCancellationPseudoRequest(new SubscriptionCancellationPseudoRequest(serverSubscriptionCycle));
        }
    }

}
