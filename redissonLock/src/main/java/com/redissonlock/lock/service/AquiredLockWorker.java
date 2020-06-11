package com.redissonlock.lock.service;

/**
 * @description: 获取锁后逻辑处理
 * @author: wangyz
 * @date: 2020/6/11 10:16
 */
public interface AquiredLockWorker<T> {
    T invokeAfterLockAquire() throws Exception;
}
