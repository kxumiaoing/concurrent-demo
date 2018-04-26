package com.demo.concurrent.cancellation;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;

/**
 * standard cancel by interrupt
 */
public class PrimeGenerator extends Thread{
    private final BlockingQueue<BigInteger> queue;

    public PrimeGenerator(BlockingQueue<BigInteger> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        BigInteger prime = BigInteger.ONE;

        try {
            while (!Thread.currentThread().isInterrupted()) {
                queue.put(prime.nextProbablePrime());
            }
        } catch (InterruptedException e) {
        }
    }

    public void cancel(){
        super.interrupt();
    }
}
