package com.fantasybaby.basic.threadpool;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 线程池子回调
 * @author FantasyBaby
 */
@Slf4j
public class ThreadPoolCallback {
    /**
     * 使用submit CallAble 返回future
     */
    public void threadPoolCallBack(){
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Callable callBack = () -> "hello world";
        log.info("start ThreadPoolCallback");
        Future callback = executorService.submit(callBack);
        try {
            log.info((String) callback.get());
            log.info("end ThreadPoolCallback");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    public void threadPoolExtend(){
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                log.info(((MyTask)r).getName()+"-"+"beforeExecute");
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                log.info(((MyTask)r).getName()+"-"+"afterExecute");
            }

            @Override
            protected void terminated() {
                log.info("terminated");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask task = new MyTask();
            task.setName("task " + i);
            pool.execute(task);
        }

        pool.shutdown();
        try {
            pool.awaitTermination(3000,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    @Data
    class MyTask implements  Runnable{
        private String name;
        @Override
        public void run() {
            log.info("start task");

        }
    }

}
