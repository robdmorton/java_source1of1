package lectureN13;

import java.math.BigInteger;
import coreservlets.*;

public class PrimeBean {
  private BigInteger prime;

  public PrimeBean() {
    this(null);
  }
  
  public PrimeBean(String lengthString) {
    int length = 150;
    try {
      length = Integer.parseInt(lengthString);
    } catch(NumberFormatException nfe) {}
    setPrime(Primes.nextPrime(Primes.random(length)));
  }

  public BigInteger getPrime() {
    return(prime);
  }

  public void setPrime(BigInteger newPrime) {
    prime = newPrime;
  }
}
