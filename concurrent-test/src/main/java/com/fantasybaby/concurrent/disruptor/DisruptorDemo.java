package com.fantasybaby.concurrent.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import lombok.Getter;

import java.nio.ByteBuffer;

/**
 * @author: liuxi
 * @time: 2019/11/11 15:00
 */
public class DisruptorDemo {

    //自定义Event
    static class LongEvent {
        @Getter
        private long value;
        public void set(long value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        //指定RingBuffer大小,
        //必须是2的N次方
        int bufferSize = 1024;
        //构建Disruptor
        Disruptor<LongEvent> disruptor
                = new Disruptor(LongEvent::new,
                bufferSize,
                DaemonThreadFactory.INSTANCE);
        //注册事件处理器
        disruptor.handleEventsWith(
                (event, sequence, endOfBatch) ->
                        System.out.println("E: "+event.getValue()));
        //启动Disruptor
        disruptor.start();

        //获取RingBuffer
        RingBuffer<LongEvent> ringBuffer
                = disruptor.getRingBuffer();
        //生产Event
        ByteBuffer bb = ByteBuffer.allocate(8);
        for (long l = 0; true; l++){
            bb.putLong(0, l);
            //生产者生产消息
            ringBuffer.publishEvent(
                    (event, sequence, buffer) ->
                            event.set(buffer.getLong(0)), bb);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
