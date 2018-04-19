package com.demo.concurrent.latch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * latch close, all threads attachted to it are idle.
 * latch open, all threads attachted to it become active.
 */
public class Race {
    private final CountDownLatch startingGun = new CountDownLatch(1);
    private final Racer[] racers;

    public Race(int num) {
        this.racers = new Racer[num];

        for (int i=0;i<num;i++){
            this.racers[i] = new Racer("racer - " + i);
        }
    }

    public void start(){
        for (int i=0;i<this.racers.length;i++){
            new Thread(this.racers[i]).start();
        }

        this.startingGun.countDown();
    }

    private class Racer implements Runnable{
        private String name;

        public Racer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                startingGun.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long startTime = System.currentTimeMillis();

            System.out.println(this.name + " start running...");

            try {
                Thread.sleep(new Random().nextInt(10) * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.name + " takes " + (System.currentTimeMillis() - startTime) + " ms");
        }
    }
}
