package be.rubus.payara.microprofile.metrics.k8s;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/kill")
public class KillResource {

    @GET
    public void doKill() {
        System.exit(-1);
    }
}
