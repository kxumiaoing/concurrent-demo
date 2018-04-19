package com.demo.concurrent.cache;

import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by xumiao on 4/19/18.
 */
public class CacheableComputer3<A,V> implements Computable<A,V>{
    private final Map<A,Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A,V> computer;

    public CacheableComputer3(Computable<A, V> computer) {
        this.computer = computer;
    }

    /**
     * defect: not thread safe and cache pollution
     * */
    @Override
    public V compute(A arg) throws InterruptedException {
        Future<V> future = this.cache.get(arg);

        if (null == future){
            Callable<V> callable = new Callable<V>() {
                @Override
                public V call() throws Exception {
                    return CacheableComputer3.this.computer.compute(arg);
                }
            };
            FutureTask<V> futureTask = new FutureTask<V>(callable);

            future = futureTask;
            this.cache.put(arg,futureTask);
            futureTask.run();
        }

        try {
            return future.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
