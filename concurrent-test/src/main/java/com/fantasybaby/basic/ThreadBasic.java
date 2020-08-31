package com.fantasybaby.basic;

/**
 * @author liuxi
 * @date2018年04月09日 12:56
 */
public class ThreadBasic {
    public void createThread(){
        Thread thread = new Thread(() ->
            System.out.println("hello world"));
        thread.start();
    }

    public void testInterrupt(){
        Thread thread = new Thread(() ->{
            int count = 0;
            while(!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(3000);
                    if(count == 3){
                        Thread.currentThread().interrupt();
                    }
                    count++;
                    System.out.println(count+"===");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
    public static void main(String[] args) {
        ThreadBasic threadBasic = new ThreadBasic();
        threadBasic.testInterrupt();
    }
}
