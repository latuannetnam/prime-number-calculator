package com.netnam.prime.utility;



import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.util.LongBitSet;

import java.util.ArrayList;

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
    private LongBitSet sieve;


    public SegementedEratos(long firstNumber, long lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        if (firstNumber==1L)
        {
            sieve = computePrimes(lastNumber);
        }
        else
        {
            sieve = computePrimes(firstNumber,lastNumber);
        }
    }

    private LongBitSet computePrimes(long limit)
    {
        final LongBitSet primes = new LongBitSet(limit);
        primes.set(2, limit);
        for (long d = 2; d <  Math.floor(Math.sqrt(limit)); d++)
        {
            if (primes.get(d))
            {
                for (long m = d * d; m < limit; m += d)
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
    private LongBitSet computePrimes(long start, long limit)
    {
        if (limit - start > Long.MAX_VALUE)
        {
            throw new IllegalArgumentException();
        }

        final long sqrtLimit = (long) Math.floor(Math.sqrt(limit));
        final LongBitSet primes = computePrimes((long) sqrtLimit);

        final LongBitSet segment = new LongBitSet(limit-start+1);
        if (0 - start >= 0)
        {
            //segment.set((int) (0 - start), false);
            segment.clear( (0 - start));
        }
        if (1 - start >= 0)
        {
            //segment.set((int) (1 - start), false);
            segment.clear( (1 - start));
        }
        segment.set( (Math.max(0, 2 - start)),  (limit - start));
        for (long d = 2; d < sqrtLimit; d++)
        {
            if (primes.get(d))
            {
                final long remainder = (long) (start % d);
                final long mStart = start - remainder + (remainder == 0 ? 0 : d);
                for (long m = Math.max(mStart, d * d); m < limit; m += d)
                {
                    segment.clear( (m - start));
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
        for (long i = sieve.nextSetBit(0); i != -1; i = sieve.nextSetBit(i + 1))
        {
            if (firstNumber==1L)
                primes.add(i);
            else
                primes.add(firstNumber+i);

        }
        return primes;
    }

    public LongBitSet getSieve() {
        return sieve;
    }

    public static void main(String[] args) {
        long f = 1L;
        //long l = 20;
        long l = 100;
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
