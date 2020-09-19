package com.fantasybaby.concurrent;

/**用object来做锁
 * 当前object必须一致
 * @author liuxi
 * @date2018年04月09日 16:17
 */
public class SynchronizedObject implements Runnable{
    static int i =0;
    public  static SynchronizedObject instance = new SynchronizedObject();
    @Override
    public void run() {
        for (int j = 0; j < 10000; j++) {
            synchronized (instance){
                i++;
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
}
