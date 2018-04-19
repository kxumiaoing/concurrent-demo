package com.demo.concurrent.barrier;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Created by xumiao on 4/19/18.
 */
public class FamilyDinner {
    private final CyclicBarrier barrier;
    private final Memeber[] memebers;

    public FamilyDinner(int num) {
        this.barrier = new CyclicBarrier(num,() -> System.out.println("dinner starts..."));
        this.memebers = new Memeber[num];

        for (int i=0;i<num;i++){
            this.memebers[i] = new Memeber("member - " + i);
        }
    }

    public void publish(){
        System.out.println("baby, please go home for a dinner.");

        for (int i=0;i<this.memebers.length;i++){
            new Thread(this.memebers[i]).start();
        }
    }

    private class Memeber implements Runnable {
        private String name;

        public Memeber(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            Random random = new Random();
            int time = random.nextInt(10);

            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(this.name + " spends " + time + "s to arriving home.");

            try {
                FamilyDinner.this.barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }
}
