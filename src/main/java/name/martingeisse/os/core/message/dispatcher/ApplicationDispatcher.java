package name.martingeisse.os.core.message.dispatcher;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.Request;

import java.util.HashMap;
import java.util.Map;

public final class ApplicationDispatcher implements RequestDispatcher {

    private final Map<Class<? extends Request>, Process> map = new HashMap<>();

    public void addMapping(Class<? extends Request> requestClass, Process process) {
        for (Class<? extends Request> otherRequestClass : map.keySet()) {
            if (requestClass.isAssignableFrom(otherRequestClass) || otherRequestClass.isAssignableFrom(requestClass)) {
                throw new RuntimeException("conflicting request class mappings (" + requestClass + " vs. " + otherRequestClass + ")");
            }
        }
        map.put(requestClass, process);
    }

    @Override
    public Process dispatch(Request request, Process sender) {
        for (Map.Entry<Class<? extends Request>, Process> entry : map.entrySet()) {
            if (entry.getKey().isInstance(request)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("no recipient for request: " + request);
    }

}
