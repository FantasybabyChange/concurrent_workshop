package com.fantasybaby.basic.util;

import java.util.concurrent.locks.LockSupport;

/**
 * @author liuxi
 * @date2018年04月16日 16:01
 */
public class LockSupportUtilTest implements Runnable{

    @Override
    public void run() {
        synchronized (LockSupportUtilTest.class){
            System.out.println("开始挂起");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            LockSupport.park();
            System.out.println("挂起结束");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread th = new Thread(new LockSupportUtilTest());
        th.start();
        System.out.println("解除挂起");
        LockSupport.unpark(th);
        System.out.println("解除挂起完成");
        //Thread.sleep(3000);

    }
}
