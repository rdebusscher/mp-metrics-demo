package be.rubus.payara.microprofile.metrics.filter;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
public class RequestTracer {

    private Set<String> activeRequests;

    @PostConstruct
    public void init() {
        activeRequests = new HashSet<>();
    }

    public String startRequest() {
        String result = UUID.randomUUID().toString();
        activeRequests.add(result);
        return result;
    }

    public void endRequest(String id) {
        activeRequests.remove(id);
    }

    public int activeCount() {
        return activeRequests.size();
    }
}
