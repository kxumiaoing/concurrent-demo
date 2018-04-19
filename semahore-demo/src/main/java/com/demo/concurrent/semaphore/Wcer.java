package com.demo.concurrent.semaphore;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by xumiao on 4/19/18.
 */
public class Wcer implements Runnable{
    private String name;
    private Semaphore seats;

    public Wcer(String name) {
        this.name = name;
    }

    public void setSeats(Semaphore seats) {
        this.seats = seats;
    }

    @Override
    public void run() {
        try {
            this.seats.acquire();

            System.out.println(this.name + " get a seat and start...");
            Thread.sleep(new Random().nextInt(10) * 1000);
            System.out.println(this.name + " finished.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.seats.release();
        }
    }
}
