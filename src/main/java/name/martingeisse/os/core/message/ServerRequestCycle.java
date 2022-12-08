package name.martingeisse.os.core.message;

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
        clientRequestCycle.respond(response);
    }

}
