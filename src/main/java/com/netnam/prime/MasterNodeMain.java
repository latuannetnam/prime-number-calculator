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
public class MasterNodeMain {

    public static void main(String[] args) {
        final Logger logger = LogManager.getLogger(MasterNodeMain.class);
        final long firstNumber = 1;
        final long lastNumber=1000000;


        Config config = ConfigFactory.load("masternode");
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
