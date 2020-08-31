package com.fantasybaby.basic.deadlock;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Map;
import java.util.Set;

/**拿到死锁的线程 并且关闭当前线程
 * @author liuxi
 * @date2018年04月12日 20:17
 */
public class DeadlockChecker {
    private static ThreadMXBean mBean =  ManagementFactory.getThreadMXBean();
    final static Runnable deadlockCheck = () -> {
        while (true){
            long[] deadlockedThreads = mBean.findDeadlockedThreads();
            if(deadlockedThreads != null){
                ThreadInfo[] threadInfos = mBean.getThreadInfo(deadlockedThreads);
                Map<Thread, StackTraceElement[]> allStackTraces = Thread.getAllStackTraces();
                Set<Thread> threads = allStackTraces.keySet();
                for (Thread thread : threads) {
                    for (ThreadInfo threadInfo : threadInfos) {
                        if(threadInfo.getThreadId() == thread.getId()){
                            System.out.println("检测到线程"+thread.getName()+"出现死锁");
                            thread.interrupt();
                        }
                    }
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };
    public static void check(){
        Thread check = new Thread(deadlockCheck);
        check.setDaemon(true);
        check.start();
    }
}
