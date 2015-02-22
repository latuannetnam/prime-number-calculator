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
    private SegementedEratos segmentedPrimeSieve;



    public PrimeNumberGenerator(long firstNumber, long lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        primeList = new ArrayList<Long>();
        segmentedPrimeSieve= new SegementedEratos(firstNumber,lastNumber);
    }
    public void calculatePrimeNumberSimpleLoop()
    {
        for (long i=firstNumber;i<=lastNumber;i++)
        {
            if (isPrime(i))
            {
                primeList.add(i);
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

    public long primeCount()
    {
        return segmentedPrimeSieve.primeCount();
    }

    public List<Long> getPrimeList() {
        primeList = segmentedPrimeSieve.getPrimes();
        return primeList;
    }
}
