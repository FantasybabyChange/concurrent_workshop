package com.fantasybaby.basic.deadlock;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过账户管理员统一管理转入和转出账户
 */
public class FixDeadlockUseAccount {
    class Account {
        // actr 应该为单例
        private BetterAllocator actr = BetterAllocator.getInstance();
        private int balance;
        // 转账
        void transfer(Account target, int amt){
            // 一次性申请转出账户和转入账户，直到成功
            while(!actr.apply(this, target));
            try{
                // 锁定转出账户
                synchronized(this){
                    // 锁定转入账户
                    synchronized(target){
                        if (this.balance > amt){
                            this.balance -= amt;
                            target.balance += amt;
                        }
                    }
                }
            } finally {
                actr.free(this, target);
            }
        }
    }
}

