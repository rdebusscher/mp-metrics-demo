package be.rubus.payara.microprofile.metrics.business;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("/shop")
@RequestScoped
public class ShopResource {

    @Inject
    private TurnOverService turnOverService;

    @Inject
    @RestClient
    private FlakyService flakyService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void buy(JsonObject payload) {
        long customerId = payload.getInt("customerId");
        JsonObject response = flakyService.getFlakyResponse(customerId);
        double amount = payload.getJsonNumber("amount").doubleValue();
        if (response.getJsonNumber("value").doubleValue() > amount) {
            turnOverService.addToSold(amount);
        } else {
            turnOverService.addToMissed(amount);
        }
    }

}
