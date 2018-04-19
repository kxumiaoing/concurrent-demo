package com.demo.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xumiao on 4/19/18.
 */
public class CacheableComputer2<A,V> implements Computable<A,V>{
    private final Map<A,V> cache = new ConcurrentHashMap<A, V>();
    private final Computable<A,V> computer;

    public CacheableComputer2(Computable<A, V> computer) {
        this.computer = computer;
    }

    /**
     * defect: not thread safe and maybe takes a long to compute
     * */
    @Override
    public V compute(A arg) throws InterruptedException {
        V result = this.cache.get(arg);

        if (null == result){
            result = this.computer.compute(arg);
            this.cache.put(arg,result);
        }

        return result;
    }
}
