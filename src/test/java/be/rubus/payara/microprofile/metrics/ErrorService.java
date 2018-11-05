package be.rubus.payara.microprofile.metrics;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.util.Map;

public interface ErrorService {

    @GET
    @Path("{customerId}")
    Map getErrorResponse(@PathParam("customerId") Long customerId);
}
