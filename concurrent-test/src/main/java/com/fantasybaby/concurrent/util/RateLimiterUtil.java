package com.fantasybaby.concurrent.util;

import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 限流器
 * guava ratelimiter
 * @author: liuxi
 * @time: 2019/11/5 20:13
 */
public class RateLimiterUtil {
    RateLimiter limiter = RateLimiter.create(3);
    ExecutorService executorService = Executors.newFixedThreadPool(1);
    public void executeTask(Runnable run){
        double acquire = limiter.acquire();
        executorService.execute(run);

    }

    public static void main(String[] args) {
        RateLimiterUtil limiterUtil = new RateLimiterUtil();
        for (int i = 0; i < 20; i++) {
            Long prev = System.nanoTime();
            limiterUtil.executeTask(()->{
                long cur = System.nanoTime();
                System.out.println( (cur-prev)/1000_000);
            });

        }

    }
}
