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

/**
 * Created by Le Anh Tuan on 08/02/2015.
 */
public class WorkerNodeMain {

    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(WorkerNodeMain.class);


        Config config = ConfigFactory.load("workernode");
        final ActorSystem system = ActorSystem.create("ClusterSystem",config);
//        ActorRef workerActor=  system.actorOf(Props.create(WorkerPrimeCalcActor.class),
//                    "worker");



    }
}
