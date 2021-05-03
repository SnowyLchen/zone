package com.cjl.basic.zone.framework.ftp.impl;


import com.cjl.basic.zone.common.exception.FtpClientException;
import com.cjl.basic.zone.common.support.FTPClientCodeEnums;
import com.cjl.basic.zone.framework.config.FTPConfig;
import com.cjl.basic.zone.framework.ftp.FtpService;
import com.cjl.basic.zone.framework.ftp.config.FTPClientPool;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.StringTokenizer;

@Service
public class FtpServiceImpl implements FtpService {

    private final Logger logger = LoggerFactory.getLogger(FtpServiceImpl.class);

    private FTPClientPool ftpClientPool;

    @Autowired
    private FTPConfig ftpConfig;

    @Bean
    public FTPClientPool initFtpClientPool() {
        ftpClientPool = new FTPClientPool(ftpConfig);
        return ftpClientPool;
    }

    @Override
    public boolean uploadFile(File localFile, String remotePath) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(localFile);
            return uploadFile(remotePath, localFile.getName(), inputStream);
        } catch (Exception e) {
            logger.error("ftp文件上传失败，e={}", e.getMessage());
            throw new FtpClientException(FTPClientCodeEnums.UPLOAD_FILE_ERROR);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean uploadFile(String pathName, String fileName, String originFileName) {
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(originFileName));
            return uploadFile(pathName, fileName, inputStream);
        } catch (Exception e) {
            logger.error("ftp文件上传失败，e={}", e.getMessage());
            throw new FtpClientException(FTPClientCodeEnums.UPLOAD_FILE_ERROR);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean uploadFile(String remotePath, String fileName, InputStream inputStream) {
        FTPClient ftpClient = null;
        if (null == inputStream) {
            return false;
        }
        try {
            ftpClient = ftpClientPool.borrowObject();
            ftpClient.setFileTransferMode(ftpClient.BINARY_FILE_TYPE);
            StringTokenizer s = new StringTokenizer(remotePath, "/");
            s.countTokens();
            String pathName = "";
            while (s.hasMoreElements()) {
                pathName = pathName + "/" + (String) s.nextElement();
                try {
                    if (!ftpClient.changeWorkingDirectory(pathName)) {
                        ftpClient.mkd(pathName);
                    }
                } catch (Exception e) {
                    System.out.println("ftp文件夹创建失败");
                }
            }
            ftpClient.changeWorkingDirectory(pathName);
            System.out.println("文件名称：" + fileName + "****文件大小：" + inputStream.available());
            ftpClient.storeFile(fileName, inputStream);
            System.out.println(ftpClient.getReplyCode());
            return false;
        } catch (Exception e) {
            e.printStackTrace();
//            logger.error("ftp文件上传失败，e={}" , e.getMessage());
            throw new FtpClientException(FTPClientCodeEnums.UPLOAD_FILE_ERROR);
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public boolean existFile(String path) {
        FTPClient ftpClient = null;
        boolean flag = false;
        FTPFile[] ftpFileArr = new FTPFile[0];
        try {
            ftpClient = ftpClientPool.borrowObject();
            ftpFileArr = ftpClient.listFiles(path);
        } catch (Exception e) {
            logger.error("ftp判断文件是否存在失败，e={}", e.getMessage());
            throw new FtpClientException(FTPClientCodeEnums.CHECK_FILE_EXIST_ERROR);
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
        }
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    @Override
    public FTPFile download(String pathname, String filename) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles(pathname);
            for (FTPFile ftpFile : ftpFiles) {
                if (filename.equals(ftpFile.getName())) {
                    return ftpFile;
                }
            }
        } catch (Exception e) {
            logger.error("ftp下载文件失败，e={}", e.getMessage());
            throw new FtpClientException(FTPClientCodeEnums.DOWNLOAD_FILE_ERROR);
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
        }
        return null;
    }

    @Override
    public boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            int result = ftpClient.dele(filename);

            flag = FTPReply.isPositiveCompletion(result);
        } catch (Exception e) {
            logger.error("ftp删除文件失败，e={}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
        }
        return flag;
    }

    @Override
    public boolean deleteDirectory(String pathname, String filename) {
        boolean flag = false;
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            flag = ftpClient.removeDirectory(filename);
        } catch (Exception e) {
            logger.error("ftp删除文件夹失败，e={}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
        }
        return flag;
    }

    @Override
    public FTPFile[] listFiles(String pathname) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            //切换FTP目录
            ftpClient.changeWorkingDirectory(pathname);
            return ftpClient.listFiles(pathname); //获取文件

        } catch (Exception e) {
            logger.error("ftp获取文件列表失败，e={}", e.getMessage());
            e.printStackTrace();
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
        }
        return new FTPFile[0];
    }

    @Override
    public boolean createDirectory(String pathname) {
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowObject();
            return ftpClient.makeDirectory(pathname);
        } catch (Exception e) {
            logger.error("ftp创建目录失败，e={}", e.getMessage());
            throw new FtpClientException(FTPClientCodeEnums.UPLOAD_DIRECTORY_ERROR);
        } finally {
            if (ftpClient != null) {
                ftpClientPool.returnObject(ftpClient);
            }
        }
    }

//    @Override
//    public void responseFile(String pathname, String filename, String downloadName , HttpServletResponse response) {
//
//        FTPFile file = FtpServiceManager.ftpService.download(pathname , filename);
//
//        OutputStream outputStream = null;
//        FTPClient ftpClient = null;
//        InputStream inputStream = null;
//        try {
//
//            if(file == null) {
//                response.setStatus(200);
//                response.getWriter().write("文件不存在");
//                return;
//            }
//            ftpClient = ftpClientPool.borrowObject();
//            //切换FTP目录
//            ftpClient.changeWorkingDirectory(pathname);
//            inputStream = ftpClient.retrieveFileStream(filename);
//
//            //前面是否传入downloadName
//            if(StringUtils.isEmpty(downloadName)){
//                response.addHeader("Content-disposition", "attachment; filename=" + new String(file.getName().getBytes("GBK"), "ISO8859-1"));
//            }else{
//                response.addHeader("Content-disposition", "attachment; filename=" + downloadName);
//            }
//
//            response.setContentType("application/octet-stream");
//            response.setContentLength((int)file.getSize());
//
//            outputStream =response.getOutputStream();
//            byte[] buffer = new byte[1024];
//            for (int n = -1; (n = inputStream.read(buffer)) != -1;) {
//                outputStream.write(buffer, 0, n);
//
//            }
//
//            inputStream.close();
//            ftpClient.completePendingCommand();
//            outputStream.flush();
//            outputStream.close();
//
//        } catch (Exception e) {
//            logger.error("ftp下载文件失败，e={}" , e.getMessage());
//            throw new FTPClientException(FTPClientCodeEnums.DOWNLOAD_FILE_ERROR);
//        }finally {
//            if(ftpClient != null) {
//                ftpClientPool.returnObject(ftpClient);
//            }
//        }
//    }


}
