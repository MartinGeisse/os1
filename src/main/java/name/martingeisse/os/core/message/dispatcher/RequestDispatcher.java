package name.martingeisse.os.core.message.dispatcher;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.request.Request;
import name.martingeisse.os.core.message.subscription.SubscriptionInitiation;

import java.util.Set;

public interface RequestDispatcher {

    Process dispatch(Request request, Process sender);
    Set<Process> dispatch(SubscriptionInitiation initiation, Process subscriber);

}
