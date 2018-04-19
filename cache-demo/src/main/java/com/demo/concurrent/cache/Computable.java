package com.demo.concurrent.cache;

/**
 * Created by xumiao on 4/19/18.
 */
public interface Computable<A,V> {
    V compute(A arg) throws InterruptedException;
}
