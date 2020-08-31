package com.fantasybaby.basic.j8new;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**通过CompletionService实现
 * ForkingCluster快速失败机制
 * @author: liuxi
 * @time: 2019/10/23 14:18
 */
public class ForkingClusterDemo {
    private Integer getAddress(Integer integer){
        try {
            Thread.sleep(integer*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(integer+"获取成功");
        return integer;
    }
    public Integer execute() throws InterruptedException, ExecutionException {
        ExecutorService service = Executors.newFixedThreadPool(3);
        CompletionService cs = new ExecutorCompletionService(service);
        List<Future> futures = Lists.newArrayList();
        futures.add(cs.submit(() -> getAddress(5)));
        futures.add(cs.submit(() -> getAddress(3)));
        futures.add(cs.submit(() -> getAddress(1)));
        try{
            for (int i = 0; i < 3; i++) {
                Integer r = (Integer) cs.take().get();
                if(r != null){
                    return r;
                }
            }
        }finally {
            futures.forEach(p->p.cancel(true));
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            Integer result = new ForkingClusterDemo().execute();
            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
