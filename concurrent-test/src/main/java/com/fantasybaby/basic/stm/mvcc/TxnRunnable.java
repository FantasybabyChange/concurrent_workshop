package com.fantasybaby.basic.stm.mvcc;


/**
 * @author: liuxi
 * @time: 2019/11/18 15:19
 */
@FunctionalInterface
public interface TxnRunnable {
    void run(ITxn txn);
}
