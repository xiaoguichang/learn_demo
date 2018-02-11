package com.xiaogch.common.redis;

public class RedisException extends Exception {

    public RedisException() {
        super();
    }

    public RedisException(String message) {
        super(message);
    }

    public RedisException(String message, Throwable cause) {
        super(message , cause);
    }

}
