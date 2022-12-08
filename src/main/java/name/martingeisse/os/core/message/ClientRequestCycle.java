package name.martingeisse.os.core.message;

import java.util.concurrent.atomic.AtomicReference;

public final class ClientRequestCycle {

    private final AtomicReference<Response> responseHolder = new AtomicReference<>();

    void respond(Response response) {
        if (!responseHolder.compareAndSet(null, response)) {
            throw new IllegalStateException("this request cycle has already been responded to");
        }
    }

    public Response getResponse() {
        return responseHolder.get();
    }

    public Response waitForResponse() {
        // TODO optimize
        try {
            while (responseHolder.get() == null) {
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return responseHolder.get();
    }

}
