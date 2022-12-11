package name.martingeisse.os.core.message.request;

public final class ServerRequestCycle {

    private final Request request;
    private final ClientRequestCycle clientRequestCycle;

    public ServerRequestCycle(Request request, ClientRequestCycle clientRequestCycle) {
        this.request = request;
        this.clientRequestCycle = clientRequestCycle;
    }

    public Request getRequest() {
        return request;
    }

    public void respond(Response response) {
        if (clientRequestCycle == null) {
            throw new RuntimeException("trying to respond to a pseudo-request");
        }
        clientRequestCycle.respond(response);
    }

}
