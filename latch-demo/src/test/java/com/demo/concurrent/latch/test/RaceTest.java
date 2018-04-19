package com.demo.concurrent.latch.test;

import com.demo.concurrent.latch.Race;

/**
 * Created by xumiao on 4/19/18.
 */
public class RaceTest {
    public static void main(String[] args) throws Exception {
        Race race = new Race(3);

        race.start();
    }
}
