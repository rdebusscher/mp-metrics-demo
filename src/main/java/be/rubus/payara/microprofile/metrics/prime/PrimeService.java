package be.rubus.payara.microprofile.metrics.prime;

import javax.annotation.PostConstruct;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.enterprise.context.ApplicationScoped;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigInteger;
import java.util.Random;

@ApplicationScoped
public class PrimeService {


    private ManagedThreadFactory threadFactory;

    @PostConstruct
    public void init() {
        try {
            InitialContext ctx = new InitialContext();
            threadFactory = (ManagedThreadFactory) ctx.lookup("java:comp/DefaultManagedThreadFactory");
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    public String getPrime(boolean shortPrime) {
        int length = shortPrime ? 20 : 55;
        BigInteger value = BigInteger.probablePrime(length, new Random());

        PrimeData data = new PrimeData(value);
        // This multithreaded stuff will not make it faster, only use much more CPUs (which we want here, just create load)
        for (int i = 0; i < 8; i++) {
            threadFactory.newThread(new PrimeThread(data)).start();
        }
        data.waitFinishCalculations();

        String isPrime = data.getPrime() ? "" : "NOT";
        return String.format("Number %s is %s a prime", value, isPrime);


    }

}
