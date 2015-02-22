package com.netnam.prime.utility;


import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * Created by latuan on 21/02/2015.
 * Based on code from: <starblue> in http://stackoverflow.com/questions/17504460/sieving-primes-up-to-1012
 */
public class SegementedEratos {
    /**
     * Computes the primes using the sieve of Eratosthenes.
     *
     * @param limit  Primes will be sieved up to but not including this limit.
     *
     * @return  A bit set where exactly the elements with prime index
     *     are set to true.
     */
    private final long firstNumber;
    private final long lastNumber;
    private List<Long> primes = new ArrayList<Long>();
    private BitSet sieve;


    public SegementedEratos(long firstNumber, long lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        if (firstNumber==1L)
        {
            sieve = computePrimes((int) lastNumber);
        }
        else
        {
            sieve = computePrimes(firstNumber,lastNumber);
        }
    }

    private BitSet computePrimes(int limit)
    {
        final BitSet primes = new BitSet();
        primes.set(0, false);
        primes.set(1, false);
        primes.set(2, limit, true);
        for (int d = 2; d < (int) Math.floor(Math.sqrt(limit)); d++)
        {
            if (primes.get(d))
            {
                for (int m = d * d; m < limit; m += d)
                {
                    primes.clear(m);
                }
            }
        }
        return primes;
    }
    /**
     * Computes the primes in a range using the sieve of Eratosthenes.
     * The size of the range must not exceed Integer.MAX_VALUE.
     *
     * @param start  The start index of the prime sieve.
     * @param limit  Primes will be sieved up to but not including this limit.
     *
     * @return  A bit set representing the integer range from start to limit.
     *     Each bit in this set is set to true if and only if
     *     the corresponding integer is prime.
     */
    private BitSet computePrimes(long start, long limit)
    {
        if (limit - start > Integer.MAX_VALUE)
        {
            throw new IllegalArgumentException();
        }

        final long sqrtLimit = (int) Math.floor(Math.sqrt(limit));
        final BitSet primes = computePrimes((int) sqrtLimit);

        final BitSet segment = new BitSet();
        if (0 - start >= 0)
        {
            segment.set((int) (0 - start), false);
        }
        if (1 - start >= 0)
        {
            segment.set((int) (1 - start), false);
        }
        segment.set((int) (Math.max(0, 2 - start)), (int) (limit - start), true);
        for (int d = 2; d < sqrtLimit; d++)
        {
            if (primes.get(d))
            {
                final int remainder = (int) (start % d);
                final long mStart = start - remainder + (remainder == 0 ? 0 : d);
                for (long m = Math.max(mStart, d * d); m < limit; m += d)
                {
                    segment.clear((int) (m - start));
                }
            }
        }
        return segment;
    }



    public long primeCount()
    {
        return sieve.cardinality();
    }




    public List<Long> getPrimes() {
        for (int i = sieve.nextSetBit(0); i != -1; i = sieve.nextSetBit(i + 1))
        {
            if (firstNumber==1L)
                primes.add(new Long(i));
            else
                primes.add(firstNumber+i);

        }
        return primes;
    }

    public BitSet getSieve() {
        return sieve;
    }

    public static void main(String[] args) {
        long f = 1;
        //long l = 20;
        long l = 1000000000;
        long strt = System.nanoTime();
        SegementedEratos segementedEratos = new SegementedEratos(f,l);
        //segementedEratos.fillPrime();
        //segementedEratos.fillPrime();

        long elpsd = System.nanoTime() - strt;
        System.out.println();
        System.out.println("Found " + segementedEratos.primeCount() + " primes up from " + f + " to " + l + " in " + elpsd / 1000000 + " milliseconds.");
        if ((l - f) <= 100) {

            System.out.print(StringUtils.join(segementedEratos.getPrimes()));
        }
    }

}
