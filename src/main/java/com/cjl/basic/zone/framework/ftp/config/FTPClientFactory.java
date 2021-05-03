package com.cjl.basic.zone.framework.ftp.config;

import com.cjl.basic.zone.framework.config.FTPConfig;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * FTP自定义工厂类
 */
public class FTPClientFactory extends BasePooledObjectFactory<FTPClient> {

    private static Logger logger = LoggerFactory.getLogger(FTPClientFactory.class);
    private FTPConfig ftpConfig;

    public FTPClientFactory(FTPConfig ftpConfig) {
        this.ftpConfig = ftpConfig;
    }

    /**
     * 新建对象
     */
    @Override
    public FTPClient create() throws Exception {
        FTPClient ftpClient = new FTPClient();
        ftpClient.setConnectTimeout(ftpConfig.getClientTimeout());
        try {
            ftpClient.connect(ftpConfig.getHost(), ftpConfig.getPort());
            int reply = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftpClient.disconnect();
                logger.error("FTPServer 拒绝连接");
                return null;
            }
            boolean result = ftpClient.login(ftpConfig.getUsername(), ftpConfig.getPassword());
            if (!result) {
                logger.error("ftpClient登陆失败!");
                throw new Exception("ftpClient登陆失败! userName:" + ftpConfig.getUsername() + " ; password:"
                        + ftpConfig.getPassword());
            }
            ftpClient.setFileType(ftpConfig.getTransferFileType());
            ftpClient.setBufferSize(ftpConfig.getBufferSize());
            ftpClient.setControlEncoding(ftpConfig.getEncoding());
            if (ftpConfig.getPassiveMode()) {
                ftpClient.enterLocalPassiveMode();
            }
//            ftpClient.changeWorkingDirectory(ftpConfig.getWorkingDirectory());
        } catch (IOException e) {
            logger.error("FTP连接失败：", e);
        }
        return ftpClient;
    }

    @Override
    public PooledObject<FTPClient> wrap(FTPClient ftpClient) {
        return new DefaultPooledObject<FTPClient>(ftpClient);
    }

    /**
     * 销毁对象
     */
    @Override
    public void destroyObject(PooledObject<FTPClient> p) throws Exception {
        FTPClient ftpClient = p.getObject();
        ftpClient.logout();
        super.destroyObject(p);
    }

    /**
     * 验证对象
     */
    @Override
    public boolean validateObject(PooledObject<FTPClient> p) {
        FTPClient ftpClient = p.getObject();
        boolean connect = false;
        try {
            connect = ftpClient.sendNoOp();
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return connect;
    }

}
