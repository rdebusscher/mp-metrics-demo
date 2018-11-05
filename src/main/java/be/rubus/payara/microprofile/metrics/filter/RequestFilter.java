package be.rubus.payara.microprofile.metrics.filter;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class RequestFilter implements ContainerRequestFilter {


    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        RequestTracer requestTracer = CDI.current().select(RequestTracer.class).get();
        requestContext.setProperty("UUID", requestTracer.startRequest());
    }
}
