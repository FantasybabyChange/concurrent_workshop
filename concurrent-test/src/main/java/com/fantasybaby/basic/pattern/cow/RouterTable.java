package com.fantasybaby.basic.pattern.cow;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 通过不变模式,每次下线就删除
 * 每次上线就加入Router
 * @author: liuxi
 * @time: 2019/10/28 11:44
 */
public class RouterTable {
    //路由表信息
    //Key:接口名
    //Value:路由集合
    ConcurrentHashMap<String, CopyOnWriteArraySet<Router>>
            rt = new ConcurrentHashMap<>();
    //根据接口名获取路由表
    public Set<Router> get(String interfaceName){
        return rt.get(interfaceName);
    }
    //删除路由
    public void remove(Router router) {
        Set<Router> set=rt.get(router.getInterfaceName());
        if (set != null) {
            set.remove(router);
        }
    }
    //增加路由
    public void add(Router router) {
        Set<Router> set = rt.computeIfAbsent(
                router.getInterfaceName(), r ->
                        new CopyOnWriteArraySet<>());
        set.add(router);
    }
    //路由信息

}
