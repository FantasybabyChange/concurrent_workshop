package com.fantasybaby.test.pool;

import com.fantasybaby.concurrent.j8new.CompletionServiceDemo;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

/**
 * @author reid.liu
 * @date 2018-06-29 15:01
 */
public class CompletionServiceTest {
    private CompletionServiceDemo demo ;
    @Before
    public void init(){
        demo = new CompletionServiceDemo();
    }
    @Test
    public void testSynchronized(){
        try {
            demo.dealUseNormal();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDealUseInExecutor(){
        try {
            demo.dealUseInExecutor();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDealUseBlockQueue(){
        try {
            demo.dealWithBlockQueue();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testDealUseCompletionService(){
        try {
            demo.dealUseCompletionService();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
