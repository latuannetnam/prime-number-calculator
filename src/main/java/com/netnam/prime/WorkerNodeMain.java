package com.netnam.prime;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import com.netnam.prime.actor.MasterPrimeCalcActor;
import com.netnam.prime.actor.WorkerPrimeCalcActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

/**
 * Created by Le Anh Tuan on 08/02/2015.
 */
public class WorkerNodeMain {

    @Option(name="-h",usage="host IP address")
    private String hostname="127.0.0.1";

    public static void main(String[] args) {
        new WorkerNodeMain().doMain(args);




    }

    private void doMain(String[] args) {

        Logger logger = LogManager.getLogger(WorkerNodeMain.class);
        CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withUsageWidth(80));

        try {
            // parse the arguments.
            parser.parseArgument(args);

        } catch( CmdLineException e ) {
            // if there's a problem in the command line,
            // you'll get this exception. this will report
            // an error message.
            logger.error(e.getMessage());
            logger.info("WorkerNodeMain [options...]");
            // print the list of available options
            parser.printUsage(System.err);
            System.err.println();

            return;
        }
        logger.debug("Master host:{}",hostname);
        final Config config = ConfigFactory.parseString("akka.cluster.seed-nodes=[\"akka.tcp://ClusterSystem@" + hostname + ":3551\"]").
                withFallback(ConfigFactory.load("workernode"));
        final ActorSystem system = ActorSystem.create("ClusterSystem",config);
    }
}
