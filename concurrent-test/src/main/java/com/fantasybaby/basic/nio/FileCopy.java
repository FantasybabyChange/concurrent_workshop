package com.fantasybaby.basic.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
@Slf4j
public class FileCopy {
    public static void copy(String from,String to ){
        log.info("start copy");
        try {
            FileInputStream input = new FileInputStream(from);
            FileOutputStream output = new FileOutputStream(to);
            FileChannel channel = input.getChannel();
            FileChannel outChannel = output.getChannel();
            ByteBuffer allocate = ByteBuffer.allocate(1024);
            while (true){
                allocate.clear();
                int read = channel.read(allocate);
                if(read == -1){
                    break;
                }
                allocate.flip();
                outChannel.write(allocate);
            }
            channel.close();
            outChannel.close();
            log.info("end copy");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        FileCopy.copy("/home/fantasybaby/download/apache-tomcat-7.0.68.tar.gz","/home/fantasybaby/hehe.tar.gz");
    }
}
