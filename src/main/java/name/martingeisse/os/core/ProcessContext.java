package name.martingeisse.os.core;

import name.martingeisse.os.core.message.dispatcher.RequestDispatcher;
import name.martingeisse.os.core.message.request.ClientRequestCycle;
import name.martingeisse.os.core.message.request.Request;
import name.martingeisse.os.core.message.request.ServerRequestCycle;
import name.martingeisse.os.core.message.subscription.ClientSubscriptionCycle;
import name.martingeisse.os.core.message.subscription.SubscriptionInitiation;

public interface ProcessContext {

    ClientRequestCycle sendRequest(Request request);
    ClientSubscriptionCycle subscribe(SubscriptionInitiation initiation);

    ServerRequestCycle receiveRequest() throws InterruptedException;
    ServerRequestCycle pollRequest();
    ServerRequestCycle pollRequest(long timeoutMilliseconds) throws InterruptedException;

    Process createUnstartedChildProcess(Code code);
    Process createUnstartedChildProcess(Code code, RequestDispatcher requestDispatcher);

    Process startChildProcess(Code code);
    Process startChildProcess(Code code, RequestDispatcher requestDispatcher);

}
