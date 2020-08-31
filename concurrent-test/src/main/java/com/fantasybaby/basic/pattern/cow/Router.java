package com.fantasybaby.basic.pattern.cow;

import lombok.Data;

/**
 * 不变模式的路由对象
 * @author: liuxi
 * @time: 2019/10/28 11:45
 */
@Data
public final class Router{

    private final String  ip;
    private final Integer port;
    private final String interfaceName;
    //构造函数
    public Router(String ip,
                  Integer port, String interfaceName){
        this.ip = ip;
        this.port = port;
        this.interfaceName = interfaceName;
    }
    //重写equals方法
    public boolean equals(Object obj){
        if (obj instanceof Router) {
            Router r = (Router)obj;
            return interfaceName.equals(r.interfaceName) &&
                    ip.equals(r.ip) &&
                    port.equals(r.port);
        }
        return false;
    }
}
