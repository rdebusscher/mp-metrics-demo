package be.rubus.payara.microprofile.metrics.time;

import be.rubus.payara.microprofile.metrics.CustomerService;
import be.rubus.payara.microprofile.metrics.filter.RequestTracer;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Random;

@Path("/flaky")
@RequestScoped
public class FlakyResource {

    private static final Random RANDOM = new Random();

    @Inject
    private RequestTracer requestTracer;

    @Inject
    private CustomerService customerService;

    @Timed
    @GET
    @Path("{customerId}")
    @Produces(MediaType.APPLICATION_JSON)

    public JsonObject getFlakyResponse(@PathParam("customerId") Long customerId) {
        long waitValue = determineWait();
        try {
            Thread.sleep(waitValue);
        } catch (InterruptedException e) {
            throw new WebApplicationException(e.getMessage());
        }
        return Json.createObjectBuilder()
                .add("id", customerId)
                .add("value", customerService.getValue(customerId))
                .build();
    }

    private long determineWait() {
        int activeCount = requestTracer.activeCount();
        return RANDOM.nextInt(activeCount * 300) + 100;
    }
}
