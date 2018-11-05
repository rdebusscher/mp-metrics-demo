package be.rubus.payara.microprofile.metrics.prime;

import java.math.BigInteger;

public class PrimeThread implements Runnable {

    private PrimeData primeData;

    public PrimeThread(PrimeData primeData) {
        this.primeData = primeData;
    }

    @Override
    public void run() {
        BigInteger divider = primeData.getNextDivider();
        while (!divider.equals(BigInteger.ZERO)) {
            if (primeData.getPrimeCandidate().remainder(divider).equals(BigInteger.ZERO)) {
                primeData.dividerFound();
            }
            divider = primeData.getNextDivider();
        }
    }
}
