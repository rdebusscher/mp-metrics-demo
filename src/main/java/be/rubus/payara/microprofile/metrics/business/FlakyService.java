package be.rubus.payara.microprofile.metrics.business;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@RegisterRestClient
public interface FlakyService {

    @GET
    @Path("{customerId}")
    @Produces(MediaType.APPLICATION_JSON)
    JsonObject getFlakyResponse(@PathParam("customerId") Long customerId);
}
