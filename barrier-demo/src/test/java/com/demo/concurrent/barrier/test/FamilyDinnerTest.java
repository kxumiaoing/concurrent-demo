package com.demo.concurrent.barrier.test;

import com.demo.concurrent.barrier.FamilyDinner;

/**
 * Created by xumiao on 4/19/18.
 */
public class FamilyDinnerTest {
    public static void main(String[] args) {
        FamilyDinner dinner = new FamilyDinner(3);

        dinner.publish();
    }
}
