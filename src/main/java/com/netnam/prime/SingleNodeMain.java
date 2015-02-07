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

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class SingleNodeMain {
    public static void main(String[] args) {
        Logger logger = LogManager.getLogger(SingleNodeMain.class);
        long firstNumber = 1;
        long lastNumber=10000000;
        Message.StartPrimeNumberCalculationMsg startPrimeNumberCalculationMsg = new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,1000);

        Config config = ConfigFactory.load("singlenode");
        final ActorSystem system = ActorSystem.create("MySystem",config);
        ActorRef primeListCalActor=  system.actorOf(Props.create(MasterPrimeCalcActor.class),
                "master");
        primeListCalActor.tell(new Message.StartPrimeNumberCalculationMsg(firstNumber,lastNumber,10,1000),null);

    }
}
