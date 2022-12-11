package name.martingeisse.os.core.message.dispatcher;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.request.Request;
import name.martingeisse.os.core.message.subscription.SubscriptionInitiation;

import java.util.Set;

public final class NullDispatcher implements RequestDispatcher {

    public static final NullDispatcher INSTANCE = new NullDispatcher();

    @Override
    public Process dispatch(Request request, Process sender) {
        throw new RuntimeException("no dispatcher");
    }

    @Override
    public Set<Process> dispatch(SubscriptionInitiation initiation, Process subscriber) {
        throw new RuntimeException("no dispatcher");
    }
}
