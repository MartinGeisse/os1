package name.martingeisse.os.core.message.dispatcher;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.Request;

public final class NullDispatcher implements RequestDispatcher {

    public static final NullDispatcher INSTANCE = new NullDispatcher();

    @Override
    public Process dispatch(Request request, Process sender) {
        throw new RuntimeException("no dispatcher");
    }

}
