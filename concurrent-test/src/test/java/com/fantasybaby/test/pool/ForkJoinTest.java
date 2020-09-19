package com.fantasybaby.test.pool;

import com.fantasybaby.concurrent.threadpool.forkjoin.ExecutorServiceCalculator;
import com.fantasybaby.concurrent.threadpool.forkjoin.ForkJoinFeedBack;
import org.junit.Test;

import java.util.stream.LongStream;

public class ForkJoinTest {
    LongStream longStream = LongStream.rangeClosed(1, 100);
    /**
     * 通过线程池来完成分治 1+。。。。100
     */
    @Test
    public void test1(){

        long sum = new ExecutorServiceCalculator().sumUp(longStream.toArray());
        System.out.println(sum);
    }

    /**
     * 通过fork/join 1+.. 100
     */
    @Test
    public void test2(){
        long sum = new ForkJoinFeedBack().sumUp(longStream.toArray());
        System.out.println(sum);
        int a = 0x7fff;
        System.out.println(a);
    }
}
