package com.fantasybaby.concurrent.threadpool;

public class MakeTeaTask {
    private String taskName;
    private Long taskTime;
    private String result;

    public MakeTeaTask(String taskName, Long taskTime, String reuslt) {
        this.taskName = taskName;
        this.taskTime = taskTime;
        this.result = reuslt;
    }

    public String doTask() {
//        System.out.println("start " + Thread.currentThread().getName() + ":" + taskName);
        try {
            Thread.sleep(taskTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end " + Thread.currentThread().getName() + ":" + taskName);
        return result;
    }
}
