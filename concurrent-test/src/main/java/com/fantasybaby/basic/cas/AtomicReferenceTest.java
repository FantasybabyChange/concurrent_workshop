package com.fantasybaby.basic.cas;

import java.util.concurrent.atomic.AtomicReference;

/**使用atomicReference保证在增加时的数据原子性
 * @author liuxi
 * @date2018年04月11日 19:54
 */
public class AtomicReferenceTest implements Runnable{
    public AtomicReference<String> atint = new AtomicReference();

    @Override
    public void run() {
        if(atint.compareAndSet("hello", "hi")){
            System.out.println(Thread.currentThread().getName()+":set success value:"+atint.get());
        }else{
            System.out.println(Thread.currentThread().getName()+":set fail");
        }

    }

    public AtomicReference<String> getAtint() {
        return atint;
    }

    public void setAtint(String str) {
        atint.set(str);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread[] ths = new Thread[10];
        AtomicReferenceTest ar = new AtomicReferenceTest();
        ar.setAtint("hello");
        for (int i = 0; i < 10; i++) {
            Thread th = new Thread(ar,i+"_name_thread");
            ths[i] = th;
        }
        for (Thread th : ths) {
            th.start();
        }
    }
}
