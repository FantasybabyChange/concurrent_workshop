package com.fantasybaby.concurrent.j8new;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author reid.liu
 * @date 2018-06-29 14:25
 */
@Slf4j
public class CompletableFutureDemo {
    public static class AskThread implements Runnable {
        CompletableFuture<Integer> re ;
        public AskThread(CompletableFuture<Integer> re) {
            this.re = re;
        }

        @Override
        public void run() {
            int myRe = 0;
            try {
                log.info("start cal");
                /**
                 * get方法会等待通知
                 */
                myRe = re.get() * re.get();
                log.info("end cal");
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(myRe);
        }
 }

    /**
     *  调用complete方法 通知完成
     */
    public static class NotifyThread implements Runnable {
        CompletableFuture<Integer> re ;
        public NotifyThread(CompletableFuture<Integer> re) {
            this.re = re;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                // 模拟长时间的计算过程
                // 告知完成结果
                log.info("start set value");
                re.complete(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 测试通知的代码
     */
    public void completableNotifyTest(){
        CompletableFuture<Integer> future = new CompletableFuture<>();
        Thread thread = new Thread(new AskThread(future));
        Thread thread1 = new Thread(new NotifyThread(future));
        thread.start();
        thread1.start();
        try {
            thread.join();
            thread1.join();
//            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public static Integer calc(Integer para) {

        try {
            // 模拟一个长时间的执行
            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return para*para;
    }

    public Integer getAsyncValue() throws ExecutionException, InterruptedException {
        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> calc(50));
        System.out.println("end call");
        return future.get();
    }

    /**
     * 通过异步编程体现任务得串行执行
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public void asyToShowSynchronize() {
        CompletableFuture.supplyAsync(() -> "hello").thenApply(a -> {
            System.out.println(a);
            return a + "1";
        }).thenCompose(a-> CompletableFuture.supplyAsync(()-> a +"compose")).
                thenAccept(a-> System.out.println(a))
                 .thenRun(()->System.out.println("endhello"));

    }
}
