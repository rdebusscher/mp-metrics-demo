package be.rubus.payara.microprofile.metrics.count;

import org.eclipse.microprofile.metrics.annotation.Counted;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.net.InetAddress;
import java.net.UnknownHostException;


@Path("/hello")
@RequestScoped
public class HelloResource {

    @GET
    @Counted(monotonic = true)
    public String sayHello() {
        String ip;
        InetAddress addr;
        try {
            addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress();
        } catch (UnknownHostException e) {
            ip = "(unknown host)";
        }
        return "Hello World from " + ip;
    }
}
