package com.fantasybaby.concurrent.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**AtomicIntegerArray保证数组每一元素都无锁
 * @author liuxi
 * @date2018年04月11日 19:54
 */
public class AtomicArrayIntegerTest implements Runnable{
    public AtomicIntegerArray atint = new AtomicIntegerArray(10);

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            for (int j = 0; j < 10; j++) {
                atint.incrementAndGet(j);
            }
        }

    }

    public AtomicIntegerArray getAtint() {
        return atint;
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicArrayIntegerTest atomicIntegerTest = new AtomicArrayIntegerTest();
        List<Thread> list = new ArrayList();
        for (int i = 0; i < 10; i++) {
            Thread th = new Thread(atomicIntegerTest);
            th.start();
            list.add(th);
        }

        list.forEach(th -> {
            try {
                th.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        AtomicIntegerArray atint = atomicIntegerTest.getAtint();
        for (int i = 0; i < 10; i++) {
            System.out.println(atint.get(i));
        }
    }
}
