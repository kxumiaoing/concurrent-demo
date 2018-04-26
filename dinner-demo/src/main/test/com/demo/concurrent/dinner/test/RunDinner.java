package com.demo.concurrent.dinner.test;

import com.demo.concurrent.dinner.Dinner;
import com.demo.concurrent.dinner.PhilosopherOnlyOneEatDinner;

/**
 * Created by xumiao on 4/26/18.
 */
public class RunDinner {
    public static void main(String[] args) {
        Dinner dinner = new PhilosopherOnlyOneEatDinner();

        dinner.start();
    }
}
