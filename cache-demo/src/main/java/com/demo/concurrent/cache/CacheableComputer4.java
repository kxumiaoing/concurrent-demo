package com.demo.concurrent.cache;

import java.util.concurrent.*;

/**
 * Created by xumiao on 4/19/18.
 */
public class CacheableComputer4<A,V> implements Computable<A,V>{
    private final ConcurrentMap<A,Future<V>> cache = new ConcurrentHashMap<A, Future<V>>();
    private final Computable<A,V> computer;

    public CacheableComputer4(Computable<A, V> computer) {
        this.computer = computer;
    }

    /**
     * defect: not thread safe and cache pollution
     * */
    @Override
    public V compute(A arg) throws InterruptedException {
        while (true) {
            Future<V> future = this.cache.get(arg);

            if (null == future) {
                Callable<V> callable = new Callable<V>() {
                    @Override
                    public V call() throws Exception {
                        return CacheableComputer4.this.computer.compute(arg);
                    }
                };
                FutureTask<V> futureTask = new FutureTask<V>(callable);

                future = this.cache.putIfAbsent(arg, futureTask);

                if (null == future){
                    future = futureTask;
                    futureTask.run();
                }
            }

            try {
                return future.get();
            } catch (CancellationException e) {
                this.cache.remove(arg,future);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
