package com.fantasybaby.basic.util;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liuxi
 * @date2018年04月16日 14:58
 */
public class CyclicBarrierUtilTest {
    public static class Solider implements Runnable{
        CyclicBarrier cb ;
        String name ;
        @Override
        public void run() {
            try {
                /**
                 * 先集合
                 */
                cb.await();
                doWork();
                /**
                 *工作完毕
                 */
                cb.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

        public Solider(CyclicBarrier cb,String name){
            this.cb = cb;
            this.name = name;
        }
        public void doWork(){
            ThreadLocalRandom random = ThreadLocalRandom.current();
            try {
                Thread.sleep(random.nextInt(4)*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                System.out.println(Thread.currentThread().getName()+"work done");
            }
        }
    }

    public static class BarrierRun implements Runnable{
        private boolean flag;
        private int n;
        public BarrierRun(boolean flag,int n){
            this.flag = flag;
            this.n = n;
        }
        @Override
        public void run() {
            if(flag){
                System.out.println("共:"+n+"集合完毕");
                flag = false;
            }else{
                System.out.println("共:"+n+"任务完成");
            }
        }
    }
    public static void main(String[] args) {
        Thread[] soliders = new Thread[10];
        CyclicBarrier cb = new CyclicBarrier(10,new BarrierRun(true,10));
        for (int i = 0; i < soliders.length; i++) {
            soliders[i] = new Thread(new Solider(cb,"solider"+i));
            soliders[i].start();
        }
    }
}
