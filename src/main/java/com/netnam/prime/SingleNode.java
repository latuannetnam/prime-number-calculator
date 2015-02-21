package com.netnam.prime;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.netnam.prime.actor.MasterPrimeCalcActor;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleNode {
    Logger logger = LogManager.getLogger(SingleNode.class);
    private long firstNumber = 1;
    private long lastNumber=1000;

    public SingleNode(long firstNumber, long lastNumber) {
        this.firstNumber = firstNumber;
        this.lastNumber = lastNumber;
        long segmentNumber=1000000;
        logger.debug("Calculating prime numbers from {} to {}", firstNumber, lastNumber);
        Config config = ConfigFactory.load("singlenode");
        final ActorSystem system = ActorSystem.create("MySystem",config);
        ActorRef primeListCalActor=  system.actorOf(Props.create(MasterPrimeCalcActor.class),
                "master");
        if ((lastNumber-firstNumber)<segmentNumber)  segmentNumber = lastNumber-firstNumber;
        primeListCalActor.tell(new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,segmentNumber),null);
    }


}
