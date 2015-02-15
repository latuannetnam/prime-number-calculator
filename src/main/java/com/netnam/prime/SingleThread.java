package com.netnam.prime;

import com.netnam.prime.utility.PrimeNumberEnumrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleThread {
    Logger logger = LogManager.getLogger(SingleThread.class);

    private long firstNumber = 1;
    private long lastNumber=1000;


    public SingleThread(long firstNumber, long lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        logger.debug("Calculating prime numbers from {} to {}", firstNumber, lastNumber);
        long startTime = System.nanoTime();

        PrimeNumberEnumrator primeNumberEnumrator = new PrimeNumberEnumrator(firstNumber,lastNumber);
        primeNumberEnumrator.calculatePrimeNumber();
        long stopTime = System.nanoTime() - startTime;
        logger.debug("prime list calculated for {} mil seconds", stopTime/1000000);
        logger.debug("Total prime numbers from {} to {} are {}", firstNumber, lastNumber, primeNumberEnumrator.getPrimeList().size());
        //logger.debug(StringUtils.join(primeNumberEnumrator.getPrimeList()));
    }


}
