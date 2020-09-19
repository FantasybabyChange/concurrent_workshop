package com.fantasybaby.concurrent.stm.mvcc;

import lombok.Getter;
import lombok.Setter;

/**
 * @author: liuxi
 * @time: 2019/11/18 10:30
 */
public class TxnRef<T> {
    @Getter
    @Setter
    private volatile  VersionRef vf;
    public TxnRef(T value){
        vf = new VersionRef(0L,value);
    }

    public T getValue(ITxn txn) {
        return txn.get(this);
    }

    public void setValue(ITxn txn,T value) {
        txn.set(this,value);
    }
}
