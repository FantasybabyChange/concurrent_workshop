package com.fantasybaby.basic.j8new;

import com.fantasybaby.basic.threadpool.MakeTeaTask;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用completable 异步编程 完成泡茶
 * @author: liuxi
 * @time: 2019/10/22 16:53
 */
public class MakeTeaUseCompletable {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        CompletableFuture<String> t2 = CompletableFuture.supplyAsync(() -> {
            MakeTeaTask makeTea1 = new MakeTeaTask("洗茶壶", 1000L, null);
            makeTea1.doTask();
            MakeTeaTask makeTea2 = new MakeTeaTask("洗茶杯", 2000L, null);
            makeTea2.doTask();
            MakeTeaTask makeTea3 = new MakeTeaTask("拿茶叶", 400L, "铁观音");
            return makeTea3.doTask();
        });
        CompletableFuture<String> t1 = CompletableFuture.runAsync(() -> {
            MakeTeaTask makeTea1 = new MakeTeaTask("洗水壶", 1000L, null);
            makeTea1.doTask();
            MakeTeaTask makeTea2 = new MakeTeaTask("烧开水", 2000L, null);
            makeTea2.doTask();
        }).thenCombine(t2, (c, a) -> {
            try {
                System.out.println(c+"");
                String s = t2.get();
                MakeTeaTask makeTea3 = new MakeTeaTask("泡茶:" + s, 2000L, "");
                makeTea3.doTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return "";
        });
        t1.join();

        service.shutdown();
    }
}
