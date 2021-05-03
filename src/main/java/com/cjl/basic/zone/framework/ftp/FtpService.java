package com.cjl.basic.zone.framework.ftp;

import org.apache.commons.net.ftp.FTPFile;

import java.io.File;
import java.io.InputStream;

/**
 * ftp服务
 */
public interface FtpService {

    boolean uploadFile(File localFile, String remotePath);

    /**
     * 上传文件
     *
     * @param pathname       ftp服务保存地址
     * @param fileName       上传到ftp的文件名
     * @param originfilename 待上传文件的名称（绝对地址） *
     * @return
     */
    boolean uploadFile(String pathname, String fileName, String originfilename);

    /**
     * 上传文件
     *
     * @param pathname    ftp服务保存地址
     * @param fileName    上传到ftp的文件名
     * @param inputStream 输入文件流
     * @return
     */
    boolean uploadFile(String pathname, String fileName, InputStream inputStream);

    //判断ftp服务器文件是否存在
    boolean existFile(String path);

    /**
     * 下载文件
     *
     * @param pathname
     * @param filename
     * @return FTPFile
     */
    FTPFile download(String pathname, String filename);

    /**
     * 删除文件 *
     *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return
     */
    boolean deleteFile(String pathname, String filename);

    /**
     * 删除文件夹，包括文件夹里面的文件（如果文件夹不为空，返回失败）
     *
     * @param pathname
     * @param filename
     * @return
     */
    boolean deleteDirectory(String pathname, String filename);

    /**
     * 获取目录下的文件信息
     *
     * @param pathname
     * @return
     */
    FTPFile[] listFiles(String pathname);

    /**
     * 创建目录
     *
     * @param pathname
     * @return
     */
    boolean createDirectory(String pathname);

//    /**
//     * 前端下载文件
//     * @param pathname
//     * @param filename
//     * @param downloadName
//     * @param response
//     */
//    void responseFile(String pathname, String filename, String downloadName, HttpServletResponse response) throws IOException;
}
