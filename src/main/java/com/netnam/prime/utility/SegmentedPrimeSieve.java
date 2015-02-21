package com.netnam.prime.utility;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
    http://blog.giovannibotta.net/search/label/sieve
    Note: there is a bug in this algorithm
 */
public class SegmentedPrimeSieve {
    private final byte sieve[];
    private final long firstNumber;
    private final long lastNumber;
    private List<Long> primes = new ArrayList<Long>();
    /**
     * Creates a segmented sieve in the interval defined by the values of start
     * and end.
     *
     * @param start
     * @param end
     */
    public SegmentedPrimeSieve(long start, long end) {
        this.firstNumber = start;
        this.lastNumber = end;
        // if the starting value is not odd, choose the next one
        start = start % 2 == 0 ? ++start : start;
        // length of the byte array
        int length = (int) ((end - start) / 16 + 1);
        sieve = new byte[length];
        // finally, let's compute the extended range
        end = start + length * 16 - 2;
        // find all the primes up to sqrt(end)
        int maxPrime = (int) Math.floor(Math.sqrt(end));
        PrimeSieve baseSieve = new PrimeSieve((int) maxPrime);
        System.out.println("Sieving numbers between " + start + " and " + end);
        // maximum value of k to sift multiples of primes in the form 2*k+1
        int maxK = maxPrime / 2;
        long intervalHalfSize = 8 * length;
        // let's assume primes in the form 2*k+1 starting from k=1
        for (int k = 1; k <= maxK; k++) {
            // if the number is marked as a prime in the basic sieve start
            // sifting all of its multiples in the given interval
            if (baseSieve.get(k)) {
                final int p = 2 * k + 1;
                // This is the initial offset to start sifting from (-start%p)
                int offset = (int) ((p - (start % p)) % p);
                // if the offset is odd, start+offset is even, skip it because
                // we don't have even numbers in the sieve. divide by two for
                // the same reason. Note that this step is crucial!
                if (offset % 2 == 1)
                {
                    offset += p;
                }
                offset /= 2;

                for (; offset < intervalHalfSize; offset += p) {
                    sieve[offset >> 3] |= (1 << (offset & 7));
                }
            }
        }


    }
    public boolean isPrime(long n) {
        if (n < firstNumber)
            throw new RuntimeException("The number " + n
                    + " is too small for the values in the sieve.");
        if (n == 2)
            return true;
        if (n == 1 || n % 2 == 0)
            return false;
        int dn = (int) (n - firstNumber);
        int i = dn / 16;
        if (i >= sieve.length)
            throw new RuntimeException("The number " + n
                    + " exceeds the values in the sieve.");
        return ((sieve[i] >> ((dn / 2) & 7)) & 1) == 0;

    }

    public void fillPrime() {
        for (long i =  firstNumber; i <= lastNumber; i++) {
            if (isPrime(i))
                primes.add(i);
        }
    }

    public List<Long> getPrimes() {
        return primes;
    }

    public static void main(String[] args) {
    long f=1;
    long l=20;
    long strt = System.nanoTime();
    SegmentedPrimeSieve segmentedPrimeSieve = new SegmentedPrimeSieve(f,l);
    segmentedPrimeSieve.fillPrime();
    long elpsd = System.nanoTime() - strt;
    System.out.println();
    System.out.println("Found " + segmentedPrimeSieve.primes.size() + " primes up from " + f + " to " + l + " in " + elpsd/1000000 + " milliseconds.");
    if ((l-f)<=100)
    {
        System.out.print(StringUtils.join( segmentedPrimeSieve.primes));
    }
}
}
