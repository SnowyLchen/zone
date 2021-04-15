package com.cjl.basic.zone.common.exception.base;

/**
 * 缓存同步时发生异常
 *
 * @author chen
 */
public class CacheSyncException extends RuntimeException {
    public CacheSyncException() {
    }

    public CacheSyncException(String message) {
        super(message);
    }

    public CacheSyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public CacheSyncException(Throwable cause) {
        super(cause);
    }

    public CacheSyncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
