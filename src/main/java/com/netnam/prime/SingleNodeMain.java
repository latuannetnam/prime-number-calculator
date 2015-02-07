package com.netnam.prime;

import com.netnam.prime.utility.PrimeNumberEnumrator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleNodeMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SingleNodeMain.class);
        long firstNumber = 1;
        long lastNumber=100;
        Message.StartPrimeNumberCalculationMsg startPrimeNumberCalculationMsg = new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,1000);



    }
}
