package com.demo.concurrent.dinner;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * there is always a philosopher who can't get a chopstick at every monment
 */
public class PhilosopherWithoutChopstick extends Thread {
    private final static Semaphore[] chopsticks = {new Semaphore(1),new Semaphore(1),new Semaphore(1),new Semaphore(1),new Semaphore(1)};
    private final static Semaphore counter = new Semaphore(4);
    private final int id;

    public PhilosopherWithoutChopstick(int id) {
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
        try {
            counter.acquire();

            try {
                getChopstick(this.id);
                getChopstick(this.id + 1);
                System.out.println(this.getName() + " eating...");
                releaseChopstick(this.id + 1);
                releaseChopstick(this.id);
            } finally {
                counter.release();
            }
        } catch (InterruptedException e) {
            eat();
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
