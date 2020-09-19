package com.fantasybaby.concurrent.threadpool.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * @author: liuxi
 * @time: 2019/10/23 15:13
 */
public class FibonacciForkJoin{
    public static void main(String[] args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        FibonacciTask fibonacciTask = new FibonacciTask(5);
        Integer result = forkJoinPool.invoke(fibonacciTask);
        System.out.println(result);
    }
    static class FibonacciTask extends RecursiveTask<Integer>{
        final int n;
        public FibonacciTask(Integer integer){
            this.n = integer;
        }
        @Override
        protected Integer compute() {
            if(n == 0 || n == 1){
                return 1;
            }else{
                FibonacciTask fibonacciTask = new FibonacciTask(n - 1);
                FibonacciTask fibonacciTask1 = new FibonacciTask(n - 2);
//                fibonacciTask.fork();
//                fibonacciTask1.fork();
                invokeAll(fibonacciTask1,fibonacciTask);
                Integer join1 = fibonacciTask.join();
                Integer join2 = fibonacciTask1.join();
                return join1 + join2;
            }
        }
    }

}
