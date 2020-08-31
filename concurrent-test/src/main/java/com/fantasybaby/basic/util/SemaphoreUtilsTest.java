package com.fantasybaby.basic.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**通过信号量 实现多线程共享资源
 * @author liuxi
 * @date2018年04月14日 22:57
 */
public class SemaphoreUtilsTest implements Runnable{
    public static Semaphore semp = new Semaphore(5);
    public static AtomicInteger integer = new AtomicInteger();
    @Override
    public void run() {
        try {
            semp.acquire();
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getName());
            int i = integer.incrementAndGet();
            System.out.println(i);
            /*if(i==5){
                integer.compareAndSet(5,0);
                System.out.println("==================");
            }*/
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            semp.release(1);
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(5,5,10000, TimeUnit.SECONDS,new LinkedBlockingDeque<Runnable>(1024));
        for (int i = 0; i < 100; i++) {
            executorService.submit(new SemaphoreUtilsTest());
        }
    }
}
