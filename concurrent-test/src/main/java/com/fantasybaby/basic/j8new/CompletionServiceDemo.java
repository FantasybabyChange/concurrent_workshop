package com.fantasybaby.basic.j8new;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 使用completionService 优化
 * 获取价格
 * 保存价格
 * 获取最低值的问题
 *
 * @author: liuxi
 * @time: 2019/10/23 12:28
 */
public class CompletionServiceDemo {
    // 创建线程池
    ExecutorService executor =
            Executors.newFixedThreadPool(6);

    private Integer getPrice(Integer value) {
        try {
            Thread.sleep(value * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(value + "获取完毕");
        return value;
    }

    private void save(Integer value) {
        try {
            System.out.println(value + "开始保存");
            Thread.sleep(value * 1000);
            System.out.println(value + "保存完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void dealUseNormal() throws ExecutionException, InterruptedException {

        // 异步向电商S1询价
        Future<Integer> f1 =
                executor.submit(
                        () -> getPrice(3));
        // 异步向电商S2询价
        Future<Integer> f2 =
                executor.submit(
                        () -> getPrice(5));
        // 异步向电商S3询价
        Future<Integer> f3 =
                executor.submit(
                        () -> getPrice(1));
        /**
         * 每个get会阻塞 性能不好
         */
        Integer f1Feedback = f1.get();
        System.out.println(f1Feedback);
        executor.execute(() ->
                save(f1Feedback));
        Integer f2Feedback = f2.get();
        System.out.println(f2Feedback);
        executor.execute(() ->
                save(f2Feedback));
        Integer f3Feedback = f3.get();
        System.out.println(f3Feedback);
        executor.execute(() ->
                save(f3Feedback));
        while (true) {

        }
    }

    public void dealUseInExecutor() throws ExecutionException, InterruptedException {
        // 异步向电商S1询价
        Future<Integer> f1 =
                executor.submit(
                        () -> getPrice(3));
        // 异步向电商S2询价
        Future<Integer> f2 =
                executor.submit(
                        () -> getPrice(5));
        // 异步向电商S3询价
        Future<Integer> f3 =
                executor.submit(
                        () -> getPrice(1));

        executor.execute(() -> {
            Integer f1Feedback = null;
            try {
                f1Feedback = f1.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            save(f1Feedback);
        });
        executor.execute(() -> {
            Integer f2Feedback = null;
            try {
                f2Feedback = f2.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            save(f2Feedback);
        });
        executor.execute(() -> {
            Integer f3Feedback = null;
            try {
                f3Feedback = f3.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            save(f3Feedback);
        });
        while (true) {

        }
    }

    public void dealWithBlockQueue() throws ExecutionException, InterruptedException {
        // 异步向电商S1询价
        Future<Integer> f1 =
                executor.submit(
                        () -> getPrice(3));
        // 异步向电商S2询价
        Future<Integer> f2 =
                executor.submit(
                        () -> getPrice(5));
        // 异步向电商S3询价
        Future<Integer> f3 =
                executor.submit(
                        () -> getPrice(1));

        // 创建阻塞队列
        BlockingQueue<Integer> bq =
                new LinkedBlockingQueue<>();
        //电商S1报价异步进入阻塞队列
        executor.execute(() ->
        {
            try {
                bq.put(f1.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        //电商S2报价异步进入阻塞队列
        executor.execute(() ->
        {
            try {
                bq.put(f2.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        //电商S3报价异步进入阻塞队列
        executor.execute(() ->
        {
            try {
                bq.put(f3.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        //异步保存所有报价
        for (int i = 0; i < 3; i++) {
            Integer r = bq.take();
            executor.execute(() -> save(r));
        }
    }

    public void dealUseCompletionService() throws InterruptedException, ExecutionException {
        // 创建线程池
        ExecutorService executor =
                Executors.newFixedThreadPool(3);
        // 创建CompletionService
        CompletionService<Integer> cs = new
                ExecutorCompletionService<>(executor);
        // 异步向电商S1询价
        cs.submit(() -> getPrice(3));
        // 异步向电商S2询价
        cs.submit(() -> getPrice(5));
        // 异步向电商S3询价
        cs.submit(() -> getPrice(1));
        // 将询价结果异步保存到数据库
        for (int i = 0; i < 3; i++) {
            Integer r = cs.take().get();
            executor.execute(() -> save(r));
        }
    }
}
