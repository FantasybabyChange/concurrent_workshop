package com.fantasybaby.test.pool;

import com.fantasybaby.basic.threadpool.ThreadDiscardPolicy;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ThreadPoolExecutor;

public class ThreadDiscardPolicyTest {
    ThreadDiscardPolicy threadDiscard;
    @Before
    public void init(){
        threadDiscard = new ThreadDiscardPolicy();
    }

    /**
     * 线程池直接拒绝
     */
    @Test
    public void testDiscardPolicy(){
        threadDiscard.discardPolicy(new ThreadPoolExecutor.DiscardPolicy());
    }

    /**
     * 使用最老任务拒绝测略
     */
    @Test
    public void testDiscardOldestPolicy(){
        threadDiscard.discardPolicy(new ThreadPoolExecutor.DiscardOldestPolicy());
    }

    /**
     * 由调用这来执行
     */
    @Test
    public void testDiscardCallerRunPolicy(){
        threadDiscard.discardPolicy(new ThreadPoolExecutor.CallerRunsPolicy());
    }
    /**
     * 直接报错
     */
    @Test
    public void testAbortPolicy(){
//        threadDiscard.discardPolicy(new ThreadPoolExecutor.AbortPolicy());
    }
}
