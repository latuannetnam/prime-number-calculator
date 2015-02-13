package com.netnam.prime;

import com.netnam.prime.utility.PrimeNumberEnumrator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleThreadMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SingleThreadMain.class);
        long firstNumber = 1;
        long lastNumber=100000000;
        long startTime = System.nanoTime();

        PrimeNumberEnumrator primeNumberEnumrator = new PrimeNumberEnumrator(firstNumber,lastNumber);
        primeNumberEnumrator.calculatePrimeNumber();
        long stopTime = System.nanoTime() - startTime;
        logger.debug("prime list calculated for {} mil seconds", stopTime/1000000);
        logger.debug("Total prime numbers from {} to {} are {}", firstNumber, lastNumber, primeNumberEnumrator.getPrimeList().size());
        //logger.debug(StringUtils.join(primeNumberEnumrator.getPrimeList()));



    }
}
