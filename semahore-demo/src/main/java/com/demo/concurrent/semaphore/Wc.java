package com.demo.concurrent.semaphore;

import java.util.concurrent.Semaphore;

/**
 * Created by xumiao on 4/19/18.
 */
public class Wc {
    private final Semaphore seats;

    public Wc(int num) {
        this.seats = new Semaphore(num);
    }

    public void accept(Wcer wcer){
        wcer.setSeats(this.seats);
        new Thread(wcer).start();
    }
}
