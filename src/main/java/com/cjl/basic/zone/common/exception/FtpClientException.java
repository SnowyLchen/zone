package com.cjl.basic.zone.common.exception;


import com.cjl.basic.zone.common.support.FTPClientCodeEnums;

/**
 * FTP客户端异常
 *
 * @author chen
 */
public class FtpClientException extends RuntimeException {
    /**
     * 错误代码
     */
    private Integer code;

    public FtpClientException(FTPClientCodeEnums ftpClientCodeEnums, String message) {
        super(message);
        this.code = ftpClientCodeEnums.getCode();
    }

    public FtpClientException(FTPClientCodeEnums ftpClientCodeEnums) {
        super(ftpClientCodeEnums.getMessage());
        this.code = ftpClientCodeEnums.getCode();
    }
}
