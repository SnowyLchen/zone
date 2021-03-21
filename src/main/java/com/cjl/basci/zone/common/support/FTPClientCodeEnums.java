package com.cjl.basci.zone.common.support;

/**
 * FTP客户端错误代码
 *
 * @author hd_zhu
 */
public enum FTPClientCodeEnums {
    /**
     * 初始化连接池失败
     */
    INIT_POOL_ERROR(501, "初始化连接池失败"),
    /**
     * 文件上传失败
     */
    UPLOAD_FILE_ERROR(502, "文件上传失败"),
    /**
     * 判断文件是否存在失败
     */
    CHECK_FILE_EXIST_ERROR(503, "判断文件是否存在失败"),
    /**
     * 下载文件失败
     */
    DOWNLOAD_FILE_ERROR(504, "下载文件失败"),
    /**
     * 创建目录失败
     */
    UPLOAD_DIRECTORY_ERROR(505, "创建目录失败");
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态说明
     */
    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    FTPClientCodeEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
