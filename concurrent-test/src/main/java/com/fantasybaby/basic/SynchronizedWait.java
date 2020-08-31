package com.fantasybaby.basic;

/**
 * wait and notify test
 * @author liuxi
 * @date2018年04月09日 16:17
 */
public class SynchronizedWait {
    public static SynchronizedWait instance = new SynchronizedWait();
    public static class T1 implements Runnable{
        @Override
        public void run() {
            System.out.println("start SynchronizedWait");
            synchronized (instance){
                try {
                    System.out.println("start wait");
                    instance.wait();
                    System.out.println("receive notify");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class T2 implements  Runnable{
        @Override
        public void run() {
            System.out.println("start SynchronizedNotify");
            synchronized (instance){
                System.out.println("start notify");
                instance.notify();
                System.out.println("end notify");
            }
            try {
                System.out.println("out of t2");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new Thread(new T1());
        Thread t2 = new Thread(new T2());
        t1.start();
        t2.start();
    }
}
