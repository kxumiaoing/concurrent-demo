package com.demo.concurrent.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xumiao on 4/19/18.
 */
public class CacheableComputer1<A,V> implements Computable<A,V>{
    private final Map<A,V> cache = new HashMap<A,V>();
    private final Computable<A,V> computer;

    public CacheableComputer1(Computable<A, V> computer) {
        this.computer = computer;
    }

    /**
     * defect: series works
     * */
    @Override
    public synchronized V compute(A arg) throws InterruptedException {
        V result = this.cache.get(arg);

        if (null == result){
            result = this.computer.compute(arg);
            this.cache.put(arg,result);
        }

        return result;
    }
}
