package ru.jewelline.request.http.config;

public final class SimpleHttpConfiguration implements HttpConfiguration {

    public static final int RETRY_COUNT = 3;
    public static final int CONNECTION_TIMEOUT = 30000;

    private int retryCount = RETRY_COUNT;
    private int connectionTimeout = CONNECTION_TIMEOUT;

    public SimpleHttpConfiguration() {
    }

    @Override
    public int getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount(int retryCount) {
        if (retryCount <= 0) {
            throw new IllegalArgumentException("Retry count can not be less then 1");
        }
        this.retryCount = retryCount;
    }

    @Override
    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        if (connectionTimeout < 0) {
            throw new IllegalArgumentException("Connection timeout can not be less then 0");
        }
        this.connectionTimeout = connectionTimeout;
    }
}