package com.fantasybaby.concurrent.cas;

import java.util.Random;
import java.util.concurrent.atomic.AtomicStampedReference;

/**使用AtomicStampedReference 重复设置值 通过stamp确保只设置一次
 * @author liuxi
 * @date2018年04月11日 19:54
 */
public class AtomicReferenceStampedTest {
    public AtomicStampedReference<Integer> atint = new AtomicStampedReference<>(10,0);
    public final int stamp = 0;
    public class Vendor implements Runnable{
        @Override
        public void run() {
            while(true){
                Integer reference = atint.getReference();
                //int stamp = atint.getStamp();
                System.out.println("vendor stamp:" + atint.getStamp());
                if(reference < 20){
                    /**
                     *
                     */
                    if(atint.compareAndSet(reference, reference + 20, stamp, stamp + 1)){
                        System.out.println("Vendor 充值成功,余额是:"+atint.getReference());
                    }else{
                        System.out.println("Vendor充值失败,余额是:"+reference);
                        //break;
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    Random ra = new Random();
    public class Customer implements Runnable{
        @Override
        public void run() {
            while(true){
                Integer reference = atint.getReference();
                int stamp = atint.getStamp();
                System.out.println("stamp :" + stamp);
                if(reference > 10){
                    int i = ra.nextInt(10);
                    if(atint.compareAndSet(reference,reference-i,stamp,stamp+1)){
                        System.out.println("Customer 消费" + i + "元成功 "+atint.getReference());
                    }else{
                        System.out.println("Customer 消费" + i + "元失败 "+reference );
                    }
                }else{
                    System.out.println("Customer 余额不足 :"+reference);
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void startLogic(){
        Thread th = new Thread(new Vendor());
        Thread th1 = new Thread(new Customer());
        th1.start();
        th.start();
    }

    public static void main(String[] args) throws InterruptedException {
        new AtomicReferenceStampedTest().startLogic();
    }

}
