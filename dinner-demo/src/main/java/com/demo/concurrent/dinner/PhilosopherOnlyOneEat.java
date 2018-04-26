package com.demo.concurrent.dinner;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * there is always a philosopher who can eat without competition at every monment
 */
public class PhilosopherOnlyOneEat extends Thread {
    private final static Semaphore[] chopsticks = {new Semaphore(1),new Semaphore(1),new Semaphore(1),new Semaphore(1),new Semaphore(1)};
    private final int id;

    public PhilosopherOnlyOneEat(int id) {
        super("philosopher - " + id);
        this.id = id;
    }

    @Override
    public void run() {
        try {
            thinking();
            eat();
        } catch (InterruptedException e) {
        } finally {
            run();
        }
    }

    private void thinking() throws InterruptedException{
        Random random = new Random();
        int time = random.nextInt(5);

        Thread.sleep(time * 1000);
        System.out.println(this.getName() + " thinks " + time + "s.");
    }

    private void eat(){
        if (0 == (this.id % 2)) {
            getChopstick(this.id);
            getChopstick(this.id + 1);
            System.out.println(this.getName() + " eating...");
            releaseChopstick(this.id + 1);
            releaseChopstick(this.id);
        } else {
            getChopstick(this.id + 1);
            getChopstick(this.id);
            System.out.println(this.getName() + " eating...");
            releaseChopstick(this.id);
            releaseChopstick(this.id + 1);
        }
    }

    private void getChopstick(int index){
        try {
            chopsticks[index % 5].acquire();
        } catch (InterruptedException e) {
            getChopstick(index);
        }
    }

    private void releaseChopstick(int index){
        chopsticks[index % 5].release();
    }
}
