package com.netnam.prime;

import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 08/02/2015.
 */
public class WorkerNode {


    private String hostname="127.0.0.1";
    private Integer port=3551;
    Logger logger = LogManager.getLogger(WorkerNode.class);

    public WorkerNode(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
        logger.debug("Worker node. Master host:{}",hostname);
        final Config config =  ConfigFactory.parseString("akka.cluster.seed-nodes=[\"akka.tcp://ClusterSystem@" + hostname + ":" +port +"\"]").
                withFallback(ConfigFactory.load("workernode"));
        final ActorSystem system = ActorSystem.create("ClusterSystem",config);
    }


}
