package name.martingeisse.os.core.message.subscription;

import name.martingeisse.os.core.message.request.Request;

/**
 * This cannot be sent as a request since it is not serializable. It is generated on the server side when a subscription
 * is initiated.
 */
public final class SubscriptionInitiationPseudoRequest extends Request {

    public final SubscriptionInitiation initiation;
    public final ServerSubscriptionCycle subscriptionCycle;

    public SubscriptionInitiationPseudoRequest(SubscriptionInitiation initiation, ServerSubscriptionCycle subscriptionCycle) {
        this.initiation = initiation;
        this.subscriptionCycle = subscriptionCycle;
    }
}
