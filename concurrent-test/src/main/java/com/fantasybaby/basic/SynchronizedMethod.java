package com.fantasybaby.basic;

/**用object来做锁
 * 当前object必须一致
 * @author liuxi
 * @date2018年04月09日 16:17
 */
public class SynchronizedMethod implements Runnable{
    static int i =0;
    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
                increase();

        }
    }

    private synchronized  void increase() {
        i++;
    }

    public static void main(String[] args) throws InterruptedException {
        SynchronizedMethod so = new SynchronizedMethod();
        Thread t1 = new Thread(so);
        Thread t2 = new Thread(so);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
