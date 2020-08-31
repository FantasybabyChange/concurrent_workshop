package com.fantasybaby.basic.stm.mvcc;
/**
 * @author: liuxi
 * @time: 2019/11/18 15:11
 */
public final class STM {
    static final Object commonLock = new Object();
    private STM(){

    }
    public static void atomic(TxnRunnable runnable){
        boolean commited = false;
        while(!commited){
            STMTxn txn = new STMTxn();
            runnable.run(txn);
            commited = txn.commit();
        }

    }
}
