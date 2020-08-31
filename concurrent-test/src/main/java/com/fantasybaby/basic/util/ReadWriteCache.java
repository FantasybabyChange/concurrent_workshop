package com.fantasybaby.basic.util;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: liuxi
 * @time: 2019/9/19 14:43
 */
public class ReadWriteCache<T> {
    private Map<String,T> cache = Maps.newHashMap();
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    private Lock readLock = rwl.readLock();
    private Lock writeLock = rwl.writeLock();
    T read(String key,T setT){
        T t ;
        readLock.lock();
        try{
            t = cache.get(key);
            if(t != null){
                return t;
            }
        }finally {
            readLock.unlock();
        }
        writeLock.lock();
        try{
            //再次查询 防止其他线程已经赋值
            t = cache.get(key);
            if(t == null){
                cache.put(key,setT);
            }
        }finally{
            writeLock.unlock();
        }
        return t;
    }
    void write(String key,T t){
        writeLock.lock();
        try{
            cache.put(key,t);
        }finally {
            writeLock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteCache<Object> objectReadWriteCache = new ReadWriteCache<>();
        objectReadWriteCache.write("1","a");
        Object b = objectReadWriteCache.read("1", "b");
        System.out.println((String)b);

    }
}
