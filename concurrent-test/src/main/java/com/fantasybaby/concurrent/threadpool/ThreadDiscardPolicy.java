package com.fantasybaby.concurrent.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 接了两个任务 后面全部都自动拒绝了
 */
@Slf4j
public class ThreadDiscardPolicy {
    int corePoolSize = 1;
    int maxPoolSize = 1;
    public void discardPolicy(RejectedExecutionHandler rejectedExecutionHandler){
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue(1);
        ThreadPoolExecutor pool = new ThreadPoolExecutor(corePoolSize,maxPoolSize,0, TimeUnit.SECONDS,workQueue);
        pool.setRejectedExecutionHandler(rejectedExecutionHandler);
        for (int i = 0; i < 10; i++) {
            final int taskIndex = i;
             Thread th = new Thread(()->{
                log.info("start task" + taskIndex);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            th.setName("ThreadName:"+i);
            pool.submit(th);
        }
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("pool shutdown");
        pool.shutdown();
        log.info("pool shutdown over");
        try {
            log.info("pool awaitTermination");
            pool.awaitTermination(1000,TimeUnit.SECONDS);
            log.info("pool end awaitTermination");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
