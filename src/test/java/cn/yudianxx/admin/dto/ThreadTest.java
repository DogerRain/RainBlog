package cn.yudianxx.admin.dto;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author huangyongwen
 * @date 2019/12/2
 * @Description
 */

public class ThreadTest extends Thread {
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 100; i++) {
            System.out.println("i=" + i);
        }
    }

    public static void main(String[] args) throws InterruptedException {
//
//        ThreadTest thead1 = new ThreadTest();
//        thead1.start();
//        thead1.sleep(5 * 1000);
//        Thread.sleep(5 * 1000); //线程睡眠
////        thead1.interrupt();
//        System.out.println("end");
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        long start = System.currentTimeMillis();
        for (int i = 1; i <= 5; i++) {
            executorService.execute(new SubThread(i));
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("所有线程执行完毕");
                System.out.println(System.currentTimeMillis() - start);
                break;
            }
            Thread.sleep(200);
//            Thread.sleep(10*1000);
        }
    }

    static class SubThread extends Thread {
        private final int i;

        public SubThread(int i) {
            this.i = i;

        }

        @Override
        public void run() {
            try {
                Thread.sleep(2*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(i);
        }
    }


}