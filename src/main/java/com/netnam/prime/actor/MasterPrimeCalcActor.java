package com.netnam.prime.actor;

import akka.actor.*;
import akka.routing.*;
import com.netnam.prime.Message;
import com.netnam.prime.utility.MapUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import scala.concurrent.duration.Duration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class MasterPrimeCalcActor extends UntypedActor {

    private Logger logger = LogManager.getLogger(MasterPrimeCalcActor.class);
    private List<Long> primeList = new ArrayList<Long>();
    private Map<String, Long> workers = new HashMap<String, Long>();
    private long sendCount = 0;
    private long receiveCount = 0;
    private long startTime = 0;
    private long stopTime = 0;
    private boolean lastTask = false;
    private long firstNumberOfAll;
    private long lastNumberOfAll;
    private long numberPerTask;
    private ActorRef router1;
    private Cancellable cancellable;
    private boolean firstTask = true;
    private long primeCount = 0;
    private long firstNumber;

    @Override
    public void preStart() {
        //create a router
        logger.debug("Master path {}", getSelf().path());
        router1 = getContext().actorOf(FromConfig.getInstance().props(Props.create(WorkerPrimeCalcActor.class)), "workerRouter");
        logger.debug("router 1 created with path {}", router1.path());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Message.StartPrimeNumberCalculationMsg) {

            Message.StartPrimeNumberCalculationMsg msg = (Message.StartPrimeNumberCalculationMsg) message;
            firstNumberOfAll = msg.getFirstNumber();
            lastNumberOfAll = msg.getLastNumber();
            numberPerTask = msg.getNumberPerTask();
            firstNumber = firstNumberOfAll;
            //Thread.sleep(2000);
            logger.debug("begin sending to workers");
            router1.tell(GetRoutees.getInstance(), getSelf());
            //cancellable = getContext().system().scheduler().schedule(Duration.create(0, TimeUnit.MILLISECONDS), Duration.create(200, TimeUnit.MILLISECONDS), router1, new Broadcast(new Message.PrepareForTask()), getContext().system().dispatcher(), getSelf());

        } else if (message instanceof Routees) {
            List<Routee> routees = ((Routees) message).getRoutees();
            logger.debug("Routees list {}", routees.size());
        } else if (message instanceof Message.ReadyForTask) {
           logger.debug("Receive response from worker {}", getSender().path());
            //cancellable.cancel();
            delegateTask(getSender());




        } else if (message instanceof Message.DoneCalculateChunkMsg) {
            Message.DoneCalculateChunkMsg msg = (Message.DoneCalculateChunkMsg) message;
            primeCount += msg.getPrimeCount();
            primeList.addAll(msg.getPrimeList());
            receiveCount++;
            String workerName = getSender().path().name();
            Long count = workers.get(workerName);
            if (count == null) {
                count = 1L;
            } else {
                count++;
            }
            workers.put(workerName, count);

            if (receiveCount == sendCount && lastTask) {
                stopTime = System.nanoTime() - startTime;
                logger.debug("prime list calculated for {} mil seconds", stopTime / 1000000);
                logger.debug("Total prime numbers from {} to {} are {}", firstNumberOfAll, lastNumberOfAll, primeCount);
                workers = MapUtil.sortByValue(workers);
                for (String worker : workers.keySet()) {
                    logger.debug("{}:{}", worker, workers.get(worker));
                }

//                router1.tell(PoisonPill.getInstance(), getSelf());
//                getSelf().tell(PoisonPill.getInstance(), null);
            } else {
              //  router1.tell(new Broadcast(new Message.PrepareForTask()), getSelf());
                delegateTask(getSender());
            }

        } else {
            unhandled(message);
        }
    }

    private void delegateTask(ActorRef sender) {
        if (firstTask) {
            startTime = System.nanoTime();
            firstTask = false;
//                do {
//                    long lastNumber = firstNumber + numberPerTask;
//                    sendCount++;
//                    if (lastNumber >= lastNumberOfAll) {
//                        lastNumber = lastNumberOfAll;
//                        lastTask = true;
//                    }
//                    router1.tell(new Message.CalculateChunkMsg(firstNumber, lastNumber), getSelf());
//                    firstNumber = lastNumber + 1;
//                    if (firstNumber >= lastNumberOfAll) {
//                        lastTask = true;
//                    }
//                } while (!lastTask);
        }
        if (lastTask) {
            //logger.debug("Done calculation. Not send task anymore");
            return;
        }

        long lastNumber = firstNumber + numberPerTask;
        sendCount++;
        if (lastNumber >= lastNumberOfAll) {
            lastNumber = lastNumberOfAll;
            lastTask = true;

        }
        sender.tell(new Message.CalculateChunkMsg(firstNumber, lastNumber), getSelf());
        firstNumber = lastNumber + 1;
        if (firstNumber >= lastNumberOfAll) {
            lastTask = true;
        }
    }
}
