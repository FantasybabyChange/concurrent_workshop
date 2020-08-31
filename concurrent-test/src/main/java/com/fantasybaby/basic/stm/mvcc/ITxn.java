package com.fantasybaby.basic.stm.mvcc;

/**
 * @author: liuxi
 * @time: 2019/11/18 10:33
 */
public interface ITxn {
    <T>T get(TxnRef<T> ref);

    <T> void set(TxnRef<T> ref,T value);
}
