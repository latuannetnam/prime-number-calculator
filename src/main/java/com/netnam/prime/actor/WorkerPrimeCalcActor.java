package com.netnam.prime.actor;

import akka.actor.UntypedActor;
import akka.routing.Broadcast;
import com.netnam.prime.Message;
import com.netnam.prime.utility.PrimeNumberEnumrator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class WorkerPrimeCalcActor extends UntypedActor {

    Logger logger = LogManager.getLogger(WorkerPrimeCalcActor.class);
    @Override
    public void preStart() {
        logger.debug("worker created with path {}",getSelf().path());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Message.CalculateChunkMsg)
        {

            Message.CalculateChunkMsg msg = (Message.CalculateChunkMsg) message;
            long firstNumber = msg.getFirstNumber();
            long lastNumber = msg.getLastNumber();
            PrimeNumberEnumrator primeNumberEnumrator = new PrimeNumberEnumrator(firstNumber,lastNumber);
            //logger.debug("{} calculating prime from {} to {}",getSelf().path(),firstNumber,lastNumber);
            primeNumberEnumrator.calculatePrimeNumber();
            getSender().tell(new Message.DoneCalculateChunkMsg(firstNumber, lastNumber, primeNumberEnumrator.getPrimeList()), getSelf());
        }

        else if (message instanceof Message.PrepareForTask)
        {
            logger.debug("Ready to receive task from Master");
            getSender().tell(new Message.ReadyForTask(),getSelf());
        }

        else {
            unhandled(message);
        }
    }
}
