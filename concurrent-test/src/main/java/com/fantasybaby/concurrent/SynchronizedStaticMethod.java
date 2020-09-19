package com.fantasybaby.concurrent;

/**用object来做锁
 * 当前object必须一致
 * @author liuxi
 * @date2018年04月09日 16:17
 */
public class SynchronizedStaticMethod implements Runnable{
    static int i =0;
    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
                increase();
        }
    }

    private static synchronized  void increase() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedStaticMethod so = new SynchronizedStaticMethod();
        SynchronizedStaticMethod so1 = new SynchronizedStaticMethod();
        Thread t1 = new Thread(so);
        Thread t2 = new Thread(so1);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
