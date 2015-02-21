package com.netnam.prime.utility;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class PrimeNumberGenerator {
    private long firstNumber;
    private long lastNumber;
    private List<Long> primeList;

    public List<Long> getPrimeList() {
        return primeList;
    }

    public PrimeNumberGenerator(long firstNumber, long lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        primeList = new ArrayList<Long>();
    }
    public void calculatePrimeNumberSimpleLoop()
    {
        for (long i=firstNumber;i<=lastNumber;i++)
        {
            if (isPrime(i))
            {
                primeList.add(new Long(i));
            }
        }
    }

    //checks whether an int is prime or not.
    public boolean isPrime(long n) {

        if (n<=3)
            return true;
        //check if n is a multiple of 2
        if (n%2==0) return false;
        //if not, then just check the odds
        for(long i=3;i*i<=n;i+=2) {
            if(n%i==0)
                return false;
        }
        return true;
    }

    public void calculatePrimeNumber() {
        SegementedEratos segmentedPrimeSieve = new SegementedEratos(firstNumber,lastNumber);
        segmentedPrimeSieve.fillSegmentPrime();
        primeList = segmentedPrimeSieve.getPrimes();
    }
}
