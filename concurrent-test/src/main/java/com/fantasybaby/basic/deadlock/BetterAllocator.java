package com.fantasybaby.basic.deadlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 使用等待通知 实现循环等待优化
 */
public class BetterAllocator {
    private static BetterAllocator allocator = new BetterAllocator();
    private List<Object> als =new ArrayList<>();
    private BetterAllocator(){
    }
    public static BetterAllocator getInstance(){
        return allocator;
    }
    // 一次性申请所有资源
    synchronized boolean apply(
            Object from, Object to){
        while(als.contains(from) ||
                als.contains(to)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            als.add(from);
            als.add(to);
            return false;
        }
        return true;
    }
    // 归还资源
    synchronized void free(
            Object from, Object to){
        als.remove(from);
        als.remove(to);
        notifyAll();
    }
}
