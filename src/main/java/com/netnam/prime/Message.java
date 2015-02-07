package com.netnam.prime;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Le Anh Tuan on 07/02/2015.
 */
public interface Message {
    public final class StartPrimeNumberCalculationMsg implements Serializable {

        private final long firstNumber;
        private final long lastNumber;
        private final long numberOfWorkers;
        private final long numberPerTask;

        public StartPrimeNumberCalculationMsg(long firstNumber, long lastNumber, long numberOfWorkers, long numberPerTask) {
            this.firstNumber = firstNumber;
            this.lastNumber = lastNumber;
            this.numberOfWorkers = numberOfWorkers;
            this.numberPerTask = numberPerTask;
        }

        public long getFirstNumber() {
            return firstNumber;
        }

        public long getLastNumber() {
            return lastNumber;
        }

        public long getNumberOfWorkers() {
            return numberOfWorkers;
        }

        public long getNumberPerTask() {
            return numberPerTask;
        }
    }

    public final class CalculateChunkMsg implements Serializable {

        private final long firstNumber;
        private final long lastNumber;

        public CalculateChunkMsg(long firstNumber, long lastNumber) {
            this.firstNumber = firstNumber;
            this.lastNumber = lastNumber;
        }

        public long getFirstNumber() {
            return firstNumber;
        }
        public long getLastNumber() {
            return lastNumber;
        }
    }

    public final class DoneCalculateChunkMsg implements Serializable {

        private final long firstNumber;
        private final long lastNumber;
        private final List<Long> primeList;


        public DoneCalculateChunkMsg(long firstNumber, long lastNumber, List<Long> primeList) {
            this.firstNumber = firstNumber;
            this.lastNumber = lastNumber;
            this.primeList = primeList;
        }

        public long getFirstNumber() {
            return firstNumber;
        }

        public List<Long> getPrimeList() {
            return primeList;
        }

        public long getLastNumber() {
            return lastNumber;
        }

    }
}
