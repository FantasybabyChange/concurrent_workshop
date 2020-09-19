package com.fantasybaby.concurrent.threadpool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 泡茶问题
 * T1处理洗水壶->烧开水->等待T2拿茶叶->泡茶
 * T2 洗茶壶->洗茶杯->拿茶叶
 *
 * @author: liuxi
 * @time: 2019/10/21 20:52
 */
public class MakeTea {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        Future<String> t2 = service.submit(() -> {
            MakeTeaTask makeTea1 = new MakeTeaTask("洗茶壶", 1000L, null);
            makeTea1.doTask();
            MakeTeaTask makeTea2 = new MakeTeaTask("洗茶杯", 2000L, null);
            makeTea2.doTask();
            MakeTeaTask makeTea3 = new MakeTeaTask("拿茶叶", 500L, "铁观音");
            return makeTea3.doTask();
        });
        service.submit(() -> {
            MakeTeaTask makeTea1 = new MakeTeaTask("洗水壶", 1000L, null);
            makeTea1.doTask();
            MakeTeaTask makeTea2 = new MakeTeaTask("烧开水", 2000L, null);
            makeTea2.doTask();
            String s;
            try {
                s = t2.get();
                MakeTeaTask makeTea3 = new MakeTeaTask("泡茶:" + s, 2000L, "");
                makeTea3.doTask();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        });
        service.shutdown();
    }
}
