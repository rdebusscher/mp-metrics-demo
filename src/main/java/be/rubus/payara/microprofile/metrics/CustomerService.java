package be.rubus.payara.microprofile.metrics;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@ApplicationScoped
public class CustomerService {

    private Map<Long, Double> customerValues;

    private Random random;

    @PostConstruct
    public void init() {
        customerValues = new HashMap<>();
        random = new Random();
    }

    public Double getValue(Long id) {
        Double result = customerValues.computeIfAbsent(id, this::initialValue);
        result += (random.nextDouble()-0.5) * 200.0;
        return result;
    }

    private Double initialValue(Long id) {
        return random.nextDouble() * 5000.0 + 500.0;
    }
}
