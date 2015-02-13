package com.netnam.prime;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.netnam.prime.actor.MasterPrimeCalcActor;
import com.netnam.prime.utility.PrimeNumberEnumrator;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleNodeMain {
    @Option(name="-f",usage="first number")
    private long firstNumber = 1;
    @Option(name="-l",usage="last number")
    private long lastNumber=1000;
    public static void main(String[] args) {
        new SingleNodeMain().doMain(args);


    }

    private void doMain(String[] args) {
        Logger logger = LogManager.getLogger(SingleNodeMain.class);
        CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withUsageWidth(80));

        try {
            // parse the arguments.
            parser.parseArgument(args);

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            logger.error(e.getMessage());
            logger.info("SingleNodeMain [options...]");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            return;
        }
        logger.debug("Calculating prime numbers from {} to {}", firstNumber, lastNumber);
        Config config = ConfigFactory.load("singlenode");
        final ActorSystem system = ActorSystem.create("MySystem",config);
        ActorRef primeListCalActor=  system.actorOf(Props.create(MasterPrimeCalcActor.class),
                "master");
        primeListCalActor.tell(new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,1000),null);
    }
}
