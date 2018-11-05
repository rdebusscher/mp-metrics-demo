package be.rubus.payara.microprofile.metrics.retry;

import be.rubus.payara.microprofile.metrics.CustomerService;
import be.rubus.payara.microprofile.metrics.filter.RequestTracer;
import org.eclipse.microprofile.faulttolerance.Retry;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/error")
@RequestScoped
public class ErrorResource {

    private static final Random RANDOM = new Random();

    @Inject
    private RequestTracer requestTracer;

    @Inject
    private CustomerService customerService;

    @Retry
    @GET
    @Path("{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getErrorResponse(@PathParam("customerId") Long customerId) {
        long waitValue = determineWait();
        try {
            Thread.sleep(waitValue);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isErrorThrown()) {
            throw new WebApplicationException("I'm not very well programmed");
        }
        return Json.createObjectBuilder()
                .add("id", customerId)
                .add("value", customerService.getValue(customerId))
                .build();
    }

    private boolean isErrorThrown() {
        int activeCount = requestTracer.activeCount();
        if (activeCount > 10) {
            return true;
        }
        double threshold = RANDOM.nextDouble() * 10;
        return threshold < activeCount;
    }

    private long determineWait() {
        int activeCount = requestTracer.activeCount();
        return RANDOM.nextInt(activeCount * 300) + 100;
    }
}
