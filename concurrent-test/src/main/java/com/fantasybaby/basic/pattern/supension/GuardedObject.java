package com.fantasybaby.basic.pattern.supension;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author: liuxi
 * @time: 2019/10/28 17:05
 */

class GuardedObject<T>{
    //受保护的对象
    T obj;
    final Lock lock =
            new ReentrantLock();
    final Condition done =
            lock.newCondition();
    final int timeout=1;
    //获取受保护对象
    T get(Predicate<T> p) {
        lock.lock();
        try {
            //MESA管程推荐写法 可以是对象是否为空
            while(!p.test(obj)){
                done.await(timeout,
                        TimeUnit.SECONDS);
            }
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }finally{
            lock.unlock();
        }
        //返回非空的受保护对象
        return obj;
    }
    //事件通知方法
    void onChanged(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        Object o = new GuardedObject().get((p) -> {
            if (p == null) {
                return false;
            }
            return true;
        });
        //mq收到消息后调用 onChange  但是不知道怎么找到对应关系
        //go.onChanged(msg)
    }

}