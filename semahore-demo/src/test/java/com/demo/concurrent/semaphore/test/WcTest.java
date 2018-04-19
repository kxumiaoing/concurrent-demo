package com.demo.concurrent.semaphore.test;

import com.demo.concurrent.semaphore.Wc;
import com.demo.concurrent.semaphore.Wcer;

/**
 * Created by xumiao on 4/19/18.
 */
public class WcTest {
    public static void main(String[] args) {
        Wc wc = new Wc(3);

        for (int i=0;i<10;i++) {
            wc.accept(new Wcer("wcer - " + i));
        }
    }
}
