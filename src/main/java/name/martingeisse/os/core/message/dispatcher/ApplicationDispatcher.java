package name.martingeisse.os.core.message.dispatcher;

import name.martingeisse.os.core.Process;
import name.martingeisse.os.core.message.request.Request;
import name.martingeisse.os.core.message.subscription.SubscriptionInitiation;

import java.util.*;

public final class ApplicationDispatcher implements RequestDispatcher {

    private final Map<Class<? extends Request>, Process> requestMap = new HashMap<>();
    private final Map<Class<? extends SubscriptionInitiation>, Set<Process>> subscriptionMap = new HashMap<>();

    public void addRequestMapping(Class<? extends Request> requestClass, Process process) {
        for (Class<? extends Request> otherRequestClass : requestMap.keySet()) {
            if (requestClass.isAssignableFrom(otherRequestClass) || otherRequestClass.isAssignableFrom(requestClass)) {
                throw new RuntimeException("conflicting request class mappings (" + requestClass + " vs. " + otherRequestClass + ")");
            }
        }
        requestMap.put(requestClass, process);
    }

    public void addSubscriptionMapping(Class<? extends SubscriptionInitiation> initiationClass, Process... processes) {
        for (Class<? extends SubscriptionInitiation> otherInitiationClass : subscriptionMap.keySet()) {
            if (initiationClass.isAssignableFrom(otherInitiationClass) || otherInitiationClass.isAssignableFrom(initiationClass)) {
                throw new RuntimeException("conflicting initiation class mappings (" + initiationClass + " vs. " + otherInitiationClass + ")");
            }
        }
        subscriptionMap.put(initiationClass, new HashSet<>(List.of(processes)));
    }

    @Override
    public Process dispatch(Request request, Process sender) {
        for (Map.Entry<Class<? extends Request>, Process> entry : requestMap.entrySet()) {
            if (entry.getKey().isInstance(request)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("no recipient for request: " + request);
    }

    @Override
    public Set<Process> dispatch(SubscriptionInitiation initiation, Process subscriber) {
        for (Map.Entry<Class<? extends SubscriptionInitiation>, Set<Process>> entry : subscriptionMap.entrySet()) {
            if (entry.getKey().isInstance(initiation)) {
                return entry.getValue();
            }
        }
        throw new RuntimeException("no recipient for initiation: " + initiation);
    }

}
