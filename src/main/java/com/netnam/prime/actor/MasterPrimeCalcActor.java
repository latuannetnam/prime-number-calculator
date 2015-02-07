package com.netnam.prime.actor;

import akka.actor.ActorRef;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.routing.FromConfig;
import com.netnam.prime.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class MasterPrimeCalcActor extends UntypedActor {

    Logger logger = LogManager.getLogger(MasterPrimeCalcActor.class);
    List<Long> primeList = new ArrayList<Long>();
    Map<String,Long> workers = new HashMap<String,Long>();
    private long sendCount =0;
    private long receiveCount =0;
    private long startTime=0;
    private long stopTime=0;
    boolean lastTask=false;
    private long firstNumberOfAll;
    private long lastNumberOfAll;
    ActorRef router1;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Message.StartPrimeNumberCalculationMsg)
        {
            startTime = System.nanoTime();
            Message.StartPrimeNumberCalculationMsg msg = (Message.StartPrimeNumberCalculationMsg) message;

            long firstNumber = msg.getFirstNumber();
            long numberOfWorkers =msg.getNumberOfWorkers();
            long numberPerTask = msg.getNumberPerTask();
            firstNumberOfAll = msg.getFirstNumber();
            lastNumberOfAll = msg.getLastNumber();

            //create a router
            router1 =  getContext().actorOf(FromConfig.getInstance().props(Props.create(WorkerPrimeCalcActor.class)),"router1");
            logger.debug("router 1 created with path {}",router1.path());

            do {
                long lastNumber = firstNumber +numberPerTask;
                sendCount++;
                if (lastNumber>=msg.getLastNumber())
                {
                    lastNumber = msg.getLastNumber();
                    lastTask=true;
                }
                router1.tell(new Message.CalculateChunkMsg(firstNumber,lastNumber),getSelf());
                firstNumber = lastNumber+1;
                if (firstNumber>=msg.getLastNumber())
                {
                    lastTask=true;
                }
            } while (lastTask==false);


        }
        else if (message instanceof Message.DoneCalculateChunkMsg)
        {
            Message.DoneCalculateChunkMsg msg = (Message.DoneCalculateChunkMsg) message;
            primeList.addAll(msg.getPrimeList());
            receiveCount++;
            String workerName = getSender().path().toString();
            Long count = workers.get(workerName);
            if (count==null)
            {
                count = new Long(1);
            }
            else
            {
                count++;
            }
            workers.put(workerName,count);

            if(receiveCount==sendCount && lastTask)
            {
                long stopTime = System.nanoTime() - startTime;
                logger.debug("prime list calculated for {} mil seconds", stopTime/1000000);
                logger.debug("Total prime numbers from {} to {} are {}", firstNumberOfAll, lastNumberOfAll, primeList.size());
                for (String worker: workers.keySet())
                {
                    logger.debug("{}:{}",worker,workers.get(worker));
                }

                router1.tell(PoisonPill.getInstance(),getSelf());
                getSelf().tell(PoisonPill.getInstance(),null);
            }

        }
        else {
            unhandled(message);
        }
    }
}
