package com.fantasybaby.concurrent.stm.mvcc;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author: liuxi
 * @time: 2019/11/18 10:34
 */
public class STMTxn implements ITxn {
    private AtomicLong txtSeq = new AtomicLong(0);
    private long txnId;
    private Map<TxnRef, VersionRef> inTxnMap = Maps.newHashMap();
    private Map<TxnRef, Object> writeMap = Maps.newHashMap();

    public STMTxn() {
        txnId = txtSeq.incrementAndGet();
    }

    @Override
    public <T> T get(TxnRef<T> ref) {
        if (!inTxnMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.getVf());
        }
        return (T) inTxnMap.get(ref).getValue();
    }

    @Override
    public <T> void set(TxnRef<T> ref, T value) {

        if (!writeMap.containsKey(ref)) {
            inTxnMap.put(ref, ref.getVf());
        }
        writeMap.put(ref, value);
    }

    boolean commit() {
        synchronized (STM.commonLock){
            boolean isValid = true;
            Set<Map.Entry<TxnRef, VersionRef>> entries = inTxnMap.entrySet();
            for (Map.Entry<TxnRef, VersionRef> entry : entries) {
                VersionRef vf = entry.getKey().getVf();
                VersionRef value = entry.getValue();
                if(vf.getVersion() != value.getVersion()){
                    isValid = false;
                    break;
                }
            }
            if(isValid){
                writeMap.forEach((k,v)-> k.setVf(new VersionRef(txnId,v)));
            }
            return isValid;
        }
    }
}
