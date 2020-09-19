package com.fantasybaby.test.pool;

import com.fantasybaby.concurrent.threadpool.ThreadPoolCallback;
import org.junit.Test;

public class ThreadPoolCallbackTest {
    @Test
    public void testCallBack(){
        new ThreadPoolCallback().threadPoolCallBack();
    }
    @Test
    public void testPoolExtend(){
        new ThreadPoolCallback().threadPoolExtend();
    }
}
