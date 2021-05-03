package com.cjl.basic.zone.framework.ftp.config;

import com.cjl.basic.zone.framework.config.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ftp连接池配置
 */

public class FTPClientPool {
    @Autowired
    FTPConfig ftpConfig;

    private static Logger logger = LoggerFactory.getLogger(FTPClientPool.class);

    private GenericObjectPool<FTPClient> ftpClientPool;

    /**
     * 初始化连接池
     *
     * @param ftpConfig
     */
    public FTPClientPool(FTPConfig ftpConfig) {

        // 初始化对象池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setBlockWhenExhausted(ftpConfig.getBlockWhenExhausted());
        poolConfig.setMaxWaitMillis(Long.parseLong(ftpConfig.getMaxWait() + ""));
        poolConfig.setMinIdle(ftpConfig.getMinIdle());
        poolConfig.setMaxIdle(ftpConfig.getMaxIdle());
        poolConfig.setMaxTotal(ftpConfig.getMaxTotal());
        poolConfig.setTestOnBorrow(ftpConfig.getTestOnBorrow());
        poolConfig.setTestOnReturn(ftpConfig.getTestOnReturn());
        poolConfig.setTestOnCreate(ftpConfig.getTestOnCreate());
        poolConfig.setTestWhileIdle(ftpConfig.getTestWhileIdle());
        poolConfig.setLifo(ftpConfig.getLifo());

//        FTPConfig ftpConfig=new FTPConfig();
        ftpConfig.setHost(ftpConfig.getHost());
        ftpConfig.setPort(ftpConfig.getPort());
        ftpConfig.setUsername(ftpConfig.getUsername());
        ftpConfig.setPassword(ftpConfig.getPassword());
        ftpConfig.setClientTimeout(ftpConfig.getClientTimeout());
        ftpConfig.setEncoding(ftpConfig.getEncoding());
//        ftpConfig.setWorkingDirectory(pro.getProperty("ftpClient_workingDirectory"));
        ftpConfig.setPassiveMode(ftpConfig.getPassiveMode());
        ftpConfig.setRenameUploaded(ftpConfig.getRenameUploaded());
        ftpConfig.setRetryTimes(ftpConfig.getRetryTimes());
        ftpConfig.setTransferFileType(ftpConfig.getTransferFileType());
        ftpConfig.setBufferSize(ftpConfig.getBufferSize());
        // 初始化对象池
        ftpClientPool = new GenericObjectPool<FTPClient>(new FTPClientFactory(ftpConfig), poolConfig);
    }

    /**
     * 获取连接
     *
     * @return
     * @throws Exception
     */
    public FTPClient borrowObject() throws Exception {
        return ftpClientPool.borrowObject();
    }

    /**
     * 释放连接
     *
     * @param ftpClient
     */
    public void returnObject(FTPClient ftpClient) {
        ftpClientPool.returnObject(ftpClient);
    }
}
