package com.demo.concurrent.dinner;


public interface Dinner {
    Thread newPhilosopher(int id);
    void start();
}
