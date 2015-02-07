package com.netnam.prime.actor;

import akka.actor.UntypedActor;
import com.netnam.prime.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public class MasterPrimeCalcActor extends UntypedActor {

    Logger logger = LogManager.getLogger(MasterPrimeCalcActor.class);
    List<Long> primeList = new ArrayList<Long>();
    private long count=0;
    private long startTime=0;
    private long stopTime=0;
    boolean lastTask=false;
    long firstNumber=3;

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof Message.StartPrimeNumberCalculationMsg)
        {
            Message.StartPrimeNumberCalculationMsg msg = (Message.StartPrimeNumberCalculationMsg) message;

        }
    }
}
