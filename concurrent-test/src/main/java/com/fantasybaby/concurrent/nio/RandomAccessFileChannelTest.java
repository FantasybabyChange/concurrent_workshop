package com.fantasybaby.concurrent.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * @author reid.liu
 * @date 2018-07-20 16:23
 */
@Slf4j
public class RandomAccessFileChannelTest {
    public void readFile(){
        try {
            Charset charset = Charset.forName("UTF-8");//Java.nio.charset.Charset处理了字符转换问题。它通过构造CharsetEncoder和CharsetDecoder将字符序列转换成字节和逆转换。
            CharsetDecoder decoder = charset.newDecoder();
            String path1 = this.getClass().getClassLoader().getResource("").getPath();
            File file1 = new File(path1).getParentFile();
//            File file1 = new File("src/main/resources/readFile.txt");
//            URL resource = this.getClass().getResource("src/main");
//            String path = this.getClass().getgetPath()+"readFile.txt";
            String path = file1.getAbsolutePath()+File.separator+"resources/readFile.txt";

            log.info(path);
            RandomAccessFile file = new RandomAccessFile(new File(path),"rw");
            FileChannel channel = file.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(48);
            CharBuffer cb = CharBuffer.allocate(512);
            int read = channel.read(buffer);
            while (read != -1){
                System.out.println("Read " + read);
                buffer.flip();
                decoder.decode(buffer,cb,false);
                cb.flip();

                while (cb.hasRemaining()) {
                    log.info(String.valueOf(cb.get()));
                }
//                while(buffer.hasRemaining()){


//                    bs[i] = b;

//                }

                buffer.clear();
                cb.clear();
                read = channel.read(buffer);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new RandomAccessFileChannelTest().readFile();
    }

}
