package com.fantasybaby.concurrent.stm.mvcc;

/**
 * @author: liuxi
 * @time: 2019/11/19 11:06
 */
public class SafeAccount {
    //余额
    private TxnRef<Integer> balance;
    //构造方法
    public SafeAccount(int balance) {
        this.balance = new TxnRef<>(balance);
    }
    //转账操作
    public void transfer(SafeAccount target, int amt){
        STM.atomic((txn)->{
            Integer from = balance.getValue(txn);
            balance.setValue(txn,from-amt);
            Integer to = target.balance.getValue(txn);
            target.balance.setValue(txn,to+amt);
            System.out.println(target.balance.getValue(txn));
            System.out.println(this.balance.getValue(txn));
        });
    }

    public static void main(String[] args) {
        SafeAccount safeAccount = new SafeAccount(10);

        SafeAccount targetAccount = new SafeAccount(50);
        safeAccount.transfer(targetAccount,10);
        System.out.println(targetAccount.balance.getVf().getValue());
        System.out.println(safeAccount.balance.getVf().getValue());
    }
}
