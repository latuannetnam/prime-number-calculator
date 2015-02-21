package com.netnam.prime.utility;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*
    http://blog.giovannibotta.net/search/label/sieve
 */
public class PrimeSieve {
    private final byte sieve[];
    private List<Long> primes = new ArrayList<Long>();
    private int maxPrime;

    /**
     * Creates a sieve of integers up to n.
     *
     * @param n
     */
    public PrimeSieve(int n) {
        this.maxPrime = n;
        // using one bit per number, skipping even numbers
        int sieveSize = n / 16 + 1;
        // round up to the next multiple of 16
        n = sieveSize * 16;
//        System.out.println("Sieving numbers up to " + n);
        // initialize the array of bytes. Each bit corresponds to an odd integer
        // between 1 and n, starting with the rightmost bit of the first byte.
        // If the bit is 0, the number is prime. Initially, all numbers are
        // assumed to be prime and some will be sifted out.
        sieve = new byte[sieveSize];
        // 1 is composite
        sieve[0] = 0x01;
        // this is the maximum starting number to search for primes in the form
        // 2*k+1
        int maxK = (int) Math.floor(Math.sqrt(n / 2));
        int nHalf = n / 2;
        // loop on numbers of the form 2*k+1
        for (int k = 1; k <= maxK; k++) {
            // if 2*k+1 is marked as prime, sift all its multiples
            if (get(k)) {
                // start from (2*k+1)^2: must divide this by two since the array
                // doesn't contain multiples of two. Thus the starting number is
                // (2*k+1)^2/2 = 2*k*(k+1). Note that this is odd.
                // the increment is 2*k+1 (since the sieve contains only odd
                // numbers, using this increment automatically skips to the next
                // odd multiple).
                final int increment = 2 * k + 1;
                for (int composite = 2 * k * (k + 1); composite < nHalf; composite += increment)
                    // the index in the array is obtained by discarding the
                    // rightmost 3 bits (or divide by 8); likewise, the position
                    // in the byte is obtained by right shifting one as many
                    // time as the number represented by the same 3 bits.
                    // Note that the function get() is implemented similarly.
                    sieve[composite >> 3] |= (1 << (composite & 7));
            }
        }

    }

    /**
     * Checks if the number 2*n+1 is marked as prime.
     *
     * @param n An integer number.
     * @return True if 2*n+1 is marked as prime, false otherwise.
     */
    public boolean get(int n) {
        return ((sieve[n >> 3] >> (n & 7)) & 1) == 0;
    }

    public void fillPrime() {
        for (int i = 1; i <= maxPrime; i++) {
            if (isPrime(i))
                primes.add(new Long(i));
        }
    }

    /**
     * @param n An integer.
     * @return True if n is prime.
     */
    public boolean isPrime(int n) {
        if (n == 2)
            return true;
        if (n == 1 || n % 2 == 0)
            return false;
        int i = n / 16;
        if (i >= sieve.length)
            throw new RuntimeException("The number " + n
                    + " exceeds the values in the sieve.");
        return ((sieve[i] >> ((n / 2) & 7)) & 1) == 0;
    }

    public List<Long> getPrimes() {
        return primes;
    }

    public static void main(String[] args) {

        int l = 100000000;
        long strt = System.nanoTime();
        PrimeSieve primeSieve = new PrimeSieve(l);
        primeSieve.fillPrime();
        long elpsd = System.nanoTime() - strt;
        System.out.println();
        System.out.println("Found " + primeSieve.primes.size() + " primes up  to " + l + " in " + elpsd / 1000000 + " milliseconds.");

        if (l <= 100) {

            System.out.print(StringUtils.join(primeSieve.primes));
        }
    }
}