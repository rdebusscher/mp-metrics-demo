package be.rubus.payara.microprofile.metrics;

import org.eclipse.microprofile.rest.client.AbstractRestClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public class UserErrorThread implements Runnable {

    private boolean active;
    private Random random;
    private ErrorService service;

    public UserErrorThread() {
        createService();
        random = new Random();
    }

    private void createService() {
        try {
            service = AbstractRestClientBuilder.newBuilder()
                    .baseUrl(new URL("http://localhost:8080/monitoring/rest/error"))
                    .build(ErrorService.class);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        active = true;
        while (active) {
            Map response = service.getErrorResponse((long) random.nextInt(100));
            //1System.out.println(response);

            int wait = random.nextInt(300);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        active = false;
    }
}
