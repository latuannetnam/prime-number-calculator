package com.netnam.prime;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import com.netnam.prime.actor.MasterPrimeCalcActor;
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
public class MasterNodeMain {
    @Option(name="-f",usage="first number")
    private long firstNumber = 1;
    @Option(name="-l",usage="last number")
    private long lastNumber=1000;
    @Option(name="-h",usage="host IP address")
    private String hostname="127.0.0.1";
    @Option(name="-p",usage="port address")
    private Integer port=3551;

    public static void main(String[] args) {
        new MasterNodeMain().doMain(args);

    }

     private void doMain(String[] args) {

            final Logger logger = LogManager.getLogger(MasterNodeMain.class);
         CmdLineParser parser = new CmdLineParser(this, ParserProperties.defaults().withUsageWidth(80));

         try {
             // parse the arguments.
             parser.parseArgument(args);

         } catch( CmdLineException e ) {
             // if there's a problem in the command line,
             // you'll get this exception. this will report
             // an error message.
             logger.error(e.getMessage());
             logger.info("MasterNodeMain [options...]");
             // print the list of available options
             parser.printUsage(System.err);
             System.err.println();

             return;
         }
         logger.debug("Calculating prime numbers from {} to {}", firstNumber, lastNumber);
            Config config = ConfigFactory.parseString("akka.remote.netty.tcp.hostname=" +hostname).
                    withFallback(ConfigFactory.parseString("akka.remote.netty.tcp.port=" +port)).
                    withFallback(ConfigFactory.parseString("akka.cluster.seed-nodes=[\"akka.tcp://ClusterSystem@" + hostname + ":" +port +"\"]")).
                    withFallback(ConfigFactory.load("masternode"));
            final ActorSystem system = ActorSystem.create("ClusterSystem",config);
            //#registerOnUp
            Cluster.get(system).registerOnMemberUp(new Runnable() {

                @Override
                public void run() {

                    ActorRef primeListCalActor=  system.actorOf(Props.create(MasterPrimeCalcActor.class),
                            "master");
                    logger.debug("master created {}",primeListCalActor.path());
                    primeListCalActor.tell(new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,1000),null);

                }
            });
            //#registerOnUp

        }

}
