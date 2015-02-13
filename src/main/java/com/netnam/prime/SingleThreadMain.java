package com.netnam.prime;

import com.netnam.prime.utility.PrimeNumberEnumrator;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleThreadMain {
    @Option(name="-f",usage="first number")
    private long firstNumber = 1;
    @Option(name="-l",usage="last number")
    private long lastNumber=1000;
    // receives other command line parameters than options
    @Argument
    private List<String> arguments = new ArrayList<String>();
    public static void main(String[] args) {
        new SingleThreadMain().doMain(args);




    }

    private void doMain(String[] args) {
        Logger logger = LogManager.getLogger(SingleThreadMain.class);

        CmdLineParser parser = new CmdLineParser(this,ParserProperties.defaults().withUsageWidth(80));

        try {
            // parse the arguments.
            parser.parseArgument(args);

            // you can parse additional arguments if you want.
            // parser.parseArgument("more","args");
            // after parsing arguments, you should check
            // if enough arguments are given.
//            if( arguments.isEmpty() )
//            {
//                firstNumber=1;
//                lastNumber=10000;
//            }


        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            logger.error(e.getMessage());
            logger.info("SingleThreadMain [options...]");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            return;
        }

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
