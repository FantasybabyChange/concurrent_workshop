package com.fantasybaby.basic.threadpool.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 使用executor线程池完成分治
 */
@Slf4j
public class ExecutorServiceCalculator implements  ICalculator{
    private int parallism;
    private ExecutorService pool;
    public ExecutorServiceCalculator(){
        parallism = Runtime.getRuntime().availableProcessors();
        log.info("getAvailableProcessors core "+parallism);
        pool = Executors.newFixedThreadPool(parallism);
    }

    /**
     * 类似于每个worker
     */
    private static class SumTask implements Callable<Long>{
        private long[] numbers;
        int from;
        int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        public Long call() {
            long sum = 0;
            for (int i = from; i <= to; i++) {
                sum += numbers[i];
            }
            return sum;
        }
    }

    /**
     * 拆分任务
     * @param numbers
     * @return
     */
    @Override
    public long sumUp(long[] numbers) {
        List<Future<Long>> results = new ArrayList();
        /**
         * 数组大小/核心数
         */
        int part = numbers.length / parallism;
        for (int i = 0; i < parallism; i++) {
            int from = i * part;
            int to = (i == parallism-1)?numbers.length -1 : (i+1)*part-1;
            results.add(pool.submit(new SumTask(numbers,from,to)));
        }
        long sum  = 0;
        for (Future<Long> result : results) {
             try {
                 sum += result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        log.info("sum is {}",sum);
        return sum;
    }


}
