package com.redissonlock.lock.controller;

import com.redissonlock.lock.common.aop.HttpLog;
import com.redissonlock.lock.service.AquiredLockWorker;
import com.redissonlock.lock.service.impl.RedisLocker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @description:
 * @author: wangyz
 * @date: 2020/6/11 11:10
 */
@RestController
@RequestMapping("/lock")
public class RedissonLockBaseController {
    @Autowired
    RedisLocker distributedLocker;

    @RequestMapping(value = "/redlock")
    @HttpLog
    public String testRedlock() throws Exception{

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(5);
        for (int i = 0; i < 5; ++i) { // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();
        }
        startSignal.countDown(); // let all threads proceed
        doneSignal.await();
        System.out.println("All processors done. Shutdown connection");
        return "redlock";
    }

    class Worker implements Runnable {
        private final CountDownLatch startSignal;
        private final CountDownLatch doneSignal;

        Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
            this.startSignal = startSignal;
            this.doneSignal = doneSignal;
        }

        public void run() {
            try {
                startSignal.await();
                distributedLocker.lock("test",new AquiredLockWorker<Object>() {

                    public Object invokeAfterLockAquire() {
                        doTask();
                        return null;
                    }

                });
            }catch (Exception e){

            }
        }

        void doTask() {
            System.out.println(Thread.currentThread().getName() + " start");
            Random random = new Random();
            int _int = random.nextInt(200);
            System.out.println(Thread.currentThread().getName() + " sleep " + _int + "millis");
            try {
                Thread.sleep(_int);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " end");
            doneSignal.countDown();
        }
    }
}