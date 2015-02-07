package com.netnam.prime;

import java.io.Serializable;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public interface Message {
    public final class StartPrimeNumberCalculationMsg implements Serializable {

        long firstNumber;
        long lastNumber;
        private final long numberOfWorkers;
        private final long numberPerTask;

        public StartPrimeNumberCalculationMsg(long firstNumber, long lastNumber, long numberOfWorkers, long numberPerTask) {
            this.firstNumber = firstNumber;
            this.lastNumber = lastNumber;
            this.numberOfWorkers = numberOfWorkers;
            this.numberPerTask = numberPerTask;
        }
    }
}
