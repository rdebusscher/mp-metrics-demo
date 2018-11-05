package be.rubus.payara.microprofile.metrics.filter;

import javax.enterprise.inject.spi.CDI;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class ResponseFilter implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        RequestTracer requestTracer = CDI.current().select(RequestTracer.class).get();

        Object uuid = requestContext.getProperty("UUID");
        // We can get here without being passed through the RequestFilter because we defined wrong URL
        if (uuid != null) {

            String id = uuid.toString();
            requestTracer.endRequest(id);
        }

    }
}
