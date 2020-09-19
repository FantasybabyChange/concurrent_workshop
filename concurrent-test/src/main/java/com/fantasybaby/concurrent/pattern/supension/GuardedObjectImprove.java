package com.fantasybaby.concurrent.pattern.supension;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * 增强版本的增加了map去维护消息id的关系
 * @author: liuxi
 * @time: 2019/10/28 17:43
 */
public class GuardedObjectImprove<T> {
        //受保护的对象
        T obj;
        final Lock lock =
                new ReentrantLock();
        final Condition done =
                lock.newCondition();
        final int timeout=2;
        //保存所有GuardedObject
        final static Map<Object, GuardedObject>
                gos=new ConcurrentHashMap<>();
        //静态方法创建GuardedObject
        static <K> GuardedObject
        create(K key){
            /**
             * 创建后需要销毁,不然会造成内存泄漏
             */
            GuardedObject go=new GuardedObject();
            gos.put(key, go);
            return go;
        }
        static <K, T> void fireEvent(K key, T obj){
            GuardedObject go=gos.remove(key);
            if (go != null){
                go.onChanged(obj);
            }
        }
        //获取受保护对象
        T get(Predicate<T> p) {
            lock.lock();
            try {
                //MESA管程推荐写法
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

//处理浏览器发来的请求
//            int id=序号生成器.get();
//            //创建一消息
//            Message msg1 = new
//                    Message(id,"{...}");
//            //创建GuardedObject实例
//            GuardedObject<Message> go=
//                    GuardedObject.create(id);
//            //发送消息
//            send(msg1);
//            //等待MQ消息
//            Message r = go.get(
//                    t->t != null);
        /*void onMessage(Message msg){
            //唤醒等待的线程
            GuardedObject.fireEvent(
                    msg.id, msg);
        }*/
    }
}
