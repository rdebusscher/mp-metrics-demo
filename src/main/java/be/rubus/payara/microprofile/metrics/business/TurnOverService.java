package be.rubus.payara.microprofile.metrics.business;

import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Gauge;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TurnOverService {

    private double sold;
    private double missed;

    public void addToSold(double value) {
        sold += value;
    }

    public void addToMissed(double value) {
        missed += value;
    }

    @Gauge(unit = MetricUnits.NONE, name = "soldValue", absolute = true)
    public double getSold() {
        return sold;
    }

    public double getMissed() {
        return missed;
    }
}
