package com.fantasybaby.basic.util;

import java.util.concurrent.*;

/**
 * 线程准备 主线程会等其他线程完成后再继续
 * @author fantasybaby
 * @date 2018/4/15
 */
public class CountDownLatchUtil implements Runnable{
    public static CountDownLatch latch = new CountDownLatch(5);
    @Override
    public void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        try {
            Thread.sleep(random.nextInt(5)*1000);
            System.out.println(Thread.currentThread().getName()+"check Complete");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            latch.countDown();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5,5,10000, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>(1024));
        for (int i = 0; i < 5; i++) {
            executorService.submit(new CountDownLatchUtil());
        }
        latch.await();
        System.out.println("start main thread");
        executorService.shutdown();
    }
}
