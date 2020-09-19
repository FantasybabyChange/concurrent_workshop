package com.fantasybaby.concurrent.monitor;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 通过集合简单实现管程的通信原理
 * @author: liuxi
 * @time: 2019/9/3 11:14
 */
public class MonitorQueue {
    /**
     * 等待队列
     */
    private List<Integer> blockList = Lists.newArrayList();
    private final int limit;
    //ReentrantLock实现互斥
    private ReentrantLock lock = new ReentrantLock();
    //条件变量
    private Condition putCondition = lock.newCondition();
    private Condition getCondition = lock.newCondition();

    public MonitorQueue(int limit) {
        this.limit = limit;
    }

    /**
     * get和set都是保证互斥的
     * @return
     */
    public Integer get() {
        lock.lock();
        try {
            /**
             * MESA的编程范式
             * 如果当前集合没有值 当前线程则等待
             */
            while (blockList.size() <= 0) {
                getCondition.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        //通知线程可以放入元素
        putCondition.signalAll();
        return blockList.remove(0);
    }

    public void set(Integer num) {
        lock.lock();
        try {
            /**
             * 集合满了则等待
             */
            while (blockList.size() == limit) {
                putCondition.await();
            }
            blockList.add(num);
            /**
             * 通知想要get的线程 当前可以去get 但是get不一定能够获取到值 还可能阻塞
             */
            getCondition.signalAll();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }
}
