package com.fantasybaby.basic.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * Channel和channel测试
 * @author reid.liu
 * @date 2018-07-31 15:43
 */
public class TransferChannelTest {
    public static void transferFrom(String filePath,String toPath){
        try {
            RandomAccessFile file = new RandomAccessFile(filePath,"rw");
            FileChannel sourceChannel = file.getChannel();

            RandomAccessFile toFile = new RandomAccessFile(toPath,"rw");
            FileChannel toChannel = toFile.getChannel();
            toChannel.transferFrom(sourceChannel,0,sourceChannel.size());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void transferTo(String filePath,String toPath){
        try {
            RandomAccessFile file = new RandomAccessFile(filePath,"rw");
            FileChannel sourceChannel = file.getChannel();

            RandomAccessFile toFile = new RandomAccessFile(toPath,"rw");
            FileChannel toChannel = toFile.getChannel();
            sourceChannel.transferTo(0,sourceChannel.size(),toChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //667175317
    //667175317
    public static void main(String[] args) {
        TransferChannelTest.transferFrom("/home/fantasybaby/download/ideaIU-2018.1.1.tar.gz","/home/fantasybaby/idea_copy.tar.gz");
        TransferChannelTest.transferTo("/home/fantasybaby/download/ideaIU-2018.1.1.tar.gz","/home/fantasybaby/idea_copy.tar.gz");

    }
}
