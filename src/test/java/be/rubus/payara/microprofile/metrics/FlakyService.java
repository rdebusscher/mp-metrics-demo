package be.rubus.payara.microprofile.metrics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Map;

public interface FlakyService {

    @GET
    @Path("{customerId}")
    Map getFlakyResponse(@PathParam("customerId") Long customerId);
}
