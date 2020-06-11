package com.redissonlock.lock.common.exception;

/**
 * @description:
 * @author: wangyz
 * @date: 2020/6/11 10:19
 */
public class UnableToAquireLockException extends RuntimeException {
    public UnableToAquireLockException() {
    }

    public UnableToAquireLockException(String message) {
        super(message);
    }

    public UnableToAquireLockException(String message, Throwable cause) {
        super(message, cause);
    }
}