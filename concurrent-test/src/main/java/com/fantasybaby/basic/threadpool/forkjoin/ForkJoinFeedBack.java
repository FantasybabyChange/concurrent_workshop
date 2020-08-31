package com.fantasybaby.basic.threadpool.forkjoin;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * fork join 有返回
 */
@Slf4j
public class ForkJoinFeedBack implements  ICalculator {
    static ForkJoinPool pool = new ForkJoinPool();
    @Override
    public long sumUp(long[] numbers) {
         return pool.invoke(new SumTask(numbers,0,numbers.length -1 ));
    }

    /**
     * 类似于每个worker
     */
    private static class SumTask extends  RecursiveTask<Long> {
        private long[] numbers;
        int from;
        int to;

        public SumTask(long[] numbers, int from, int to) {
            this.numbers = numbers;
            this.from = from;
            this.to = to;
        }

        @Override
        protected Long compute() {
            log.info("poolSiz,{}",pool.getPoolSize());
            if(to - from < 6){
                long total = 0;
                for (int i = from; i <= to  ; i++) {
                   total += numbers[i];
                }
                return total;
            }else{
                int middleIndex = (from+to)/2;
                SumTask sumTaskLeft = new SumTask(numbers,from,middleIndex);
                SumTask sumTaskRight = new SumTask(numbers,middleIndex+1,to);
                sumTaskLeft.fork();
                sumTaskRight.fork();
                return sumTaskLeft.join() + sumTaskRight.join();
            }
        }
    }

}
