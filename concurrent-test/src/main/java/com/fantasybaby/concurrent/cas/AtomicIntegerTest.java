package com.fantasybaby.concurrent.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**使用atomicInteger保证在增加时的数据原子性
 * @author liuxi
 * @date2018年04月11日 19:54
 */
public class AtomicIntegerTest implements Runnable{
    public  AtomicInteger atint = new AtomicInteger();

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            atint.getAndIncrement();
        }
    }

    public AtomicInteger getAtint() {
        return atint;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicIntegerTest atomicIntegerTest = new AtomicIntegerTest();
        Thread th = new Thread(atomicIntegerTest);
        Thread th2 = new Thread(atomicIntegerTest);
        Thread th3 = new Thread(atomicIntegerTest);
        th3.start();
        th2.start();
        th.start();
        th.join();
        th2.join();
        th3.join();
        System.out.println(atomicIntegerTest.getAtint());
    }
}
