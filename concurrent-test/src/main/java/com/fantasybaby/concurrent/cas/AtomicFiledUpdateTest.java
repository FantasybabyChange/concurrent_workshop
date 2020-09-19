package com.fantasybaby.concurrent.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**AtomicIntegerFieldUpdater可以让一个对象的int属性享受无锁
 * 该属性必须有volatile声明
 * @author liuxi
 * @date2018年04月11日 19:54
 */
public class AtomicFiledUpdateTest implements  Runnable{
    private AtomicIntegerFieldUpdater<Child> childFiledUpdate = AtomicIntegerFieldUpdater.newUpdater(Child.class,"ageCount");
    private Child child =new Child();
    public class Child{
         volatile int ageCount;

        public int getAgeCount() {
            return ageCount;
        }


    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            childFiledUpdate.incrementAndGet(child);
        }

    }
    public Child getChild(){
        return this.child;
    }

    public static void main(String[] args) {
        AtomicFiledUpdateTest atomicIntegerTest = new AtomicFiledUpdateTest();
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
        System.out.println(atomicIntegerTest.getChild().getAgeCount());
    }
}
