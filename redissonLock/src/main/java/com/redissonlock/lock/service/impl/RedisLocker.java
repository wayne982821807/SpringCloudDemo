package com.redissonlock.lock.service.impl;

import com.redissonlock.lock.common.exception.UnableToAquireLockException;
import com.redissonlock.lock.common.util.RedissonConnector;
import com.redissonlock.lock.service.AquiredLockWorker;
import com.redissonlock.lock.service.DistributedLocker;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @description:
 * @author: wangyz
 * @date: 2020/6/11 10:24
 */
@Service
public class RedisLocker implements DistributedLocker {
    private final static String LOCKER_PREFIX = "lock:";
    @Autowired
    RedissonConnector redissonConnector;

    public <T> T lock(String resourceName, AquiredLockWorker<T> worker) throws UnableToAquireLockException, Exception {
        return lock(resourceName, worker, 100);
    }

    public <T> T lock(String resourceName, AquiredLockWorker<T> worker, int lockTime) throws UnableToAquireLockException, Exception {
        RedissonClient redissonClient=redissonConnector.getClient();
        RLock lock=redissonClient.getLock(LOCKER_PREFIX+resourceName);
        boolean success=lock.tryLock(100,lockTime, TimeUnit.SECONDS);
        if (success){
            try {
                return worker.invokeAfterLockAquire();
            } finally {
                lock.unlock();
            }
        }
        throw new UnableToAquireLockException();
    }
}