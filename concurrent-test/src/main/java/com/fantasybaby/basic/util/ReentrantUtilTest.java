package com.fantasybaby.basic.util;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author liuxi
 * @date2018年04月12日 17:51
 */
public class ReentrantUtilTest implements Runnable{
    private ReentrantLock lock = new ReentrantLock();
    private int a = 0;
    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            lock.lock();
            lock.lock();
            try{
                a++;
            }finally {
                lock.unlock();
                lock.unlock();
            }
        }
    }

    public int getA() {
        return a;
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantUtilTest uTest = new ReentrantUtilTest();
        Thread t1 = new Thread(uTest);
        Thread t2 = new Thread(uTest);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(uTest.getA());
    }
}
