package be.rubus.payara.microprofile.metrics.prime;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;

@Path("prime")
@RequestScoped
public class PrimeResource {

    @Resource
    private ManagedExecutorService executor;


    @Inject
    private PrimeService primeService;

    @GET
    public void getLargePrime(@Suspended final AsyncResponse asyncResponse,
                              @DefaultValue("false")
                              @QueryParam("short") boolean shortPrime) {
        executor.execute(() -> {
            String result = primeService.getPrime(shortPrime);
            asyncResponse.resume(result);
        });
    }
}
