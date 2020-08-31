package com.fantasybaby.test.pool;

import com.fantasybaby.basic.j8new.CompletableFutureDemo;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * @author reid.liu
 * @date 2018-06-29 15:01
 */
public class CompletableFutureTest {
    private CompletableFutureDemo demo ;
    @Before
    public void init(){
        demo = new CompletableFutureDemo();
    }

    /**
     * 通知等待测试
     */
    @Test
    public void testNotify(){
        demo.completableNotifyTest();
    }
    /**
     * 异步执行
     */
    @Test
    public void testAsyncGet(){
        try {
            Integer asyncValue = demo.getAsyncValue();
            System.out.println(asyncValue);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testSynApply(){
        demo.asyToShowSynchronize();
    }
}
