package com.fantasybaby.basic.util;


import com.fantasybaby.basic.deadlock.DeadlockChecker;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以被中断
 * @author liuxi
 * @date2018年04月12日 17:51
 */
public class ReentrantInterruptiblyTest implements Runnable{
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private int lockStatus = 0;
    public ReentrantInterruptiblyTest(int lockStatus){
        this.lockStatus = lockStatus;
    }
    @Override
    public void run() {
        try {
            //造成死锁
            if(lockStatus == 0){
               lock1.lockInterruptibly();
               Thread.sleep(1000);
               lock2.lockInterruptibly();
            }else{
                lock2.lockInterruptibly();
                Thread.sleep(1000);
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock1.isHeldByCurrentThread()){
                lock1.unlock();
            }
            if(lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.println(Thread.currentThread().getName()+"结束");
        }
    }


    public static void main(String[] args) throws InterruptedException {
        ReentrantInterruptiblyTest uTest1 = new ReentrantInterruptiblyTest(0);
        ReentrantInterruptiblyTest uTest2 = new ReentrantInterruptiblyTest(1);
        Thread t1 = new Thread(uTest1,"th1");
        Thread t2 = new Thread(uTest2,"th2");
        t1.start();
        t2.start();
//        t1.join();
//        t2.join();
        DeadlockChecker.check();
    }
}
