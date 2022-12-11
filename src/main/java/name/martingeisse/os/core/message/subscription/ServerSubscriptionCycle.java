package name.martingeisse.os.core.message.subscription;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.request.Response;

public final class ServerSubscriptionCycle {

    private final ClientSubscriptionCycle clientSubscriptionCycle;
    public final Process process;

    public ServerSubscriptionCycle(ClientSubscriptionCycle clientSubscriptionCycle, Process process) {
        this.clientSubscriptionCycle = clientSubscriptionCycle;
        this.process = process;
    }

    public void sendResponse(Response response) {
        clientSubscriptionCycle.responseQueue.add(response);
    }

}
