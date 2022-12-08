package name.martingeisse.os.core;

import name.martingeisse.os.core.message.ClientRequestCycle;
import name.martingeisse.os.core.message.Request;
import name.martingeisse.os.core.message.ServerRequestCycle;
import name.martingeisse.os.core.message.dispatcher.RequestDispatcher;

public interface ProcessContext {

    ClientRequestCycle sendRequest(Request request);

    ServerRequestCycle receiveRequest() throws InterruptedException;
    ServerRequestCycle pollRequest();
    ServerRequestCycle pollRequest(long timeoutMilliseconds) throws InterruptedException;

    Process createUnstartedChildProcess(Code code);
    Process createUnstartedChildProcess(Code code, RequestDispatcher requestDispatcher);

    Process startChildProcess(Code code);
    Process startChildProcess(Code code, RequestDispatcher requestDispatcher);

}
