package com.demo.concurrent.dinner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xumiao on 4/26/18.
 */
public class PhilosopherWithoutChopstickDinner implements Dinner {
    @Override
    public Thread newPhilosopher(int id) {
        return new PhilosopherWithoutChopstick(id);
    }

    @Override
    public void start() {
        ExecutorService exec = Executors.newFixedThreadPool(5);

        for (int i=0;i<5;i++){
            exec.submit(newPhilosopher(i));
        }
    }
}
