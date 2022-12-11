package name.martingeisse.os.core.message.subscription;

import name.martingeisse.os.core.message.request.Request;

/**
 * This cannot be sent as a request since it is not serializable. It is generated on the server side when a subscription
 * is cancelled.
 */
public final class SubscriptionCancellationPseudoRequest extends Request {

    public final ServerSubscriptionCycle subscriptionCycle;

    public SubscriptionCancellationPseudoRequest(ServerSubscriptionCycle subscriptionCycle) {
        this.subscriptionCycle = subscriptionCycle;
    }
}
