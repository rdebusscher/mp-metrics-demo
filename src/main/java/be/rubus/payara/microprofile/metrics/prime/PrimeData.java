package be.rubus.payara.microprofile.metrics.prime;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;

public class PrimeData {

    public static Object LOCK = new Object();

    private CountDownLatch countDownLatch;

    private BigInteger primeCandidate;
    private Boolean isPrime;

    private BigInteger currentDivider;
    private BigInteger maxDivider;

    public PrimeData(BigInteger primeCandidate) {
        this.primeCandidate = primeCandidate;

        checkDivideBy2();
        if (isPrime == null) {
            countDownLatch = new CountDownLatch(1);
            currentDivider = BigInteger.ONE;

            maxDivider = bigIntSqRootCeil(primeCandidate);
        }
    }

    private void checkDivideBy2() {
        if (primeCandidate.remainder(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
            isPrime = Boolean.FALSE;
        }
    }

    public BigInteger getPrimeCandidate() {
        return primeCandidate;
    }

    public Boolean getPrime() {
        return isPrime;
    }

    public BigInteger getNextDivider() {
        BigInteger result = BigInteger.ZERO;
        synchronized (LOCK) {
            if (isPrime == null) {
                currentDivider = currentDivider.add(BigInteger.valueOf(2));
                if (currentDivider.compareTo(maxDivider) < 1) {
                    result = currentDivider;
                } else {
                    isPrime = Boolean.TRUE;
                    countDownLatch.countDown();
                }
            }
        }
        return result;
    }

    public void dividerFound() {
        synchronized (LOCK) {
            isPrime = Boolean.FALSE;
        }
    }

    public void waitFinishCalculations() {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace(); // FIXME
        }
    }

    public static BigInteger bigIntSqRootCeil(BigInteger x)
            throws IllegalArgumentException {
        if (x.compareTo(BigInteger.ZERO) < 0) {
            throw new IllegalArgumentException("Negative argument.");
        }
        // square roots of 0 and 1 are trivial and
        // y == 0 will cause a divide-by-zero exception
        if (BigInteger.ZERO.equals(x) || BigInteger.ONE.equals(x)) {
            return x;
        } // end if
        BigInteger two = BigInteger.valueOf(2L);
        BigInteger y;
        // starting with y = x / 2 avoids magnitude issues with x squared
        for (y = x.divide(two);
             y.compareTo(x.divide(y)) > 0;
             y = ((x.divide(y)).add(y)).divide(two))
            ;
        if (x.compareTo(y.multiply(y)) == 0) {
            return y;
        } else {
            return y.add(BigInteger.ONE);
        }
    }
}
