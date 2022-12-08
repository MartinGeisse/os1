package name.martingeisse.os.core.message.dispatcher;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.Request;

public interface RequestDispatcher {

    Process dispatch(Request request, Process sender);

}
