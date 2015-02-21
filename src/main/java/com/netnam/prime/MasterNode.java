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

/**
 * Created by Le Anh Tuan on 08/02/2015.
 */
public class MasterNode {
    final Logger logger = LogManager.getLogger(MasterNode.class);
    private final long firstNumber;
    private final long lastNumber;
    private final String hostname;
    private final Integer port;

    public MasterNode(final long firstNumber, final long lastNumber, String hostname, Integer port) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        this.hostname = hostname;
        this.port = port;
        final long segmentNumber=1000000;
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
                primeListCalActor.tell(new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,segmentNumber),null);

            }
        });
        //#registerOnUp
    }


}
