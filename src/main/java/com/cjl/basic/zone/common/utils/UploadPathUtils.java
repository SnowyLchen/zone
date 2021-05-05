package com.cjl.basic.zone.common.utils;

import com.cjl.basic.zone.common.controller.ZImage;
import com.cjl.basic.zone.framework.ftp.FtpService;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件上传路径生成
 */
public class UploadPathUtils {
    /**
     * 图片最终生成的目录结构：host(服务器域名或主机IP)+文件根目录(虚拟目录)+图片目录+日期目录+图片名称(含后缀)
     * 图片最终存储结构示例：http://ip/uploadBaseDir/productPic/20190215/abc.jpg
     * 注意：上传业务处理时需要根据服务器系统是windows还是linux配置物理路径和虚拟路径的映射
     */

    private final static SimpleDateFormat YYYY = new SimpleDateFormat("yyyy");
    private final static SimpleDateFormat MM = new SimpleDateFormat("MM");
    private final static SimpleDateFormat DD = new SimpleDateFormat("dd");

    private static String getYear(Date date) {
        if (null == date) {
            date = new Date();
        }
        return YYYY.format(date);
    }

    private static String getMM(Date date) {
        if (null == date) {
            date = new Date();
        }
        return MM.format(date);
    }

    private static String getDD(Date date) {
        if (null == date) {
            date = new Date();
        }
        return DD.format(date);
    }

    /*按照日期格式动态生成日期目录*/
    private static String getDateDir() {
        Date date = new Date();
        StringBuilder path = new StringBuilder("/");
        path.append(getYear(date));
        path.append(getMM(date));
        path.append(getDD(date));
        return path.toString();
    }

    /*动态生成图片上传完整目录(物理路径)*/
    public static String getPicUploadDir() {
        StringBuilder host = new StringBuilder("/home/ftp/baseUpload");
        host.append("/image");
        host.append(getDateDir());
        return generatorDir(host);
    }

    /*动态生成目录*/
    private static String generatorDir(StringBuilder dir) {
        String fileDir = dir.toString();
        if (!StringUtils.isEmpty(fileDir)) {
            File file = new File(fileDir);
            if (!file.isDirectory() && !file.exists()) {
                file.mkdirs();
            }
        }
        return fileDir;
    }

    /**
     * 压缩图片
     *
     * @param file
     */
    public static ZImage thumbnail(MultipartFile file, FtpService ftpService) {
        long startTime = System.currentTimeMillis();
        //得到上传时的原文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀名
        String suffixName = originalFilename.substring(originalFilename.lastIndexOf("."));
        //获取文件格式
        String ext = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //获取uuid作为文件名
        String name = UUID.randomUUID().toString().replaceAll("-", "");
        String saveName = name + suffixName;
        //存储目录
        String picDir = UploadPathUtils.getPicUploadDir();
        //图片存储全路径
        String outputPath = picDir + '/' + saveName;
        InputStream inputStream = null;
        try {
            System.out.println("原图片:" + originalFilename + ",大小:" + file.getSize() / 1024 + "kb");
            int i = 1;
            //生成目标图片流
            BufferedImage bufferedImage = Thumbnails.of(file.getInputStream()).scale(1f).asBufferedImage();
            //压缩图片至指定大小500k下
            bufferedImage = commpressPicCycle(500, getAccuracy(500), bufferedImage, startTime, i);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, ext, os);
            inputStream = new ByteArrayInputStream(os.toByteArray());
            //上传文件
            if (!ftpService.uploadFile(picDir, saveName, inputStream)) {
                System.out.println("图片上传至ftp失败");
                return null;
            }
            ZImage zImage = new ZImage();
            zImage.setSrc(outputPath.split("ftp")[1]);
            zImage.setTitle(saveName);
            return zImage;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("上传出现异常");
            return null;
        }
    }

    /**
     * 压缩文件至指定大小
     *
     * @param desFileSize 指定大小
     * @param accuracy    比率
     **/
    private static BufferedImage commpressPicCycle(long desFileSize, double accuracy, BufferedImage bufferedImage, long startTime, int i) throws IOException {
        System.out.println("********************图片压缩开始******************");
//        File srcFileJPG = new File(desPath);
        byte[] data = ((DataBufferByte) bufferedImage.getData().getDataBuffer()).getData();
        //如果小于指定大小不压缩；如果大于等于指定大小压缩
        if ((data.length / 1024) <= desFileSize) {
            if (i == 1) {
                System.out.println("********************图片大小小于500k,不进行压缩******************");
                System.out.println("********************图片压缩结束******************");
            } else {
                System.out.println("********************共压缩" + i + "次******************");
                long endTime = System.currentTimeMillis();
                System.out.println("图片压缩共计耗时：" + (endTime - startTime) + "毫秒");
                System.out.println("********************图片压缩结束******************");
            }
            return bufferedImage;
        }
        System.out.println("********************开始第" + i + "次压缩*****文件大小" + data.length + "kb*************");
        // 计算宽高
        int desWidth = new BigDecimal(bufferedImage.getWidth()).multiply(new BigDecimal(accuracy)).intValue();
        int desHeight = new BigDecimal(bufferedImage.getHeight()).multiply(new BigDecimal(accuracy)).intValue();
        bufferedImage = Thumbnails.of(bufferedImage).size(desWidth, desHeight).outputQuality(accuracy).asBufferedImage();
        i++;
        return commpressPicCycle(desFileSize, accuracy, bufferedImage, startTime, i);
    }

    /**
     * 自动调节精度(经验数值)
     *
     * @param size 源图片大小
     * @return 图片压缩质量比
     */
    private static double getAccuracy(long size) {
        double accuracy;
        if (size < 900) {
            accuracy = 0.85;
        } else if (size < 2047) {
            accuracy = 0.6;
        } else if (size < 3275) {
            accuracy = 0.44;
        } else {
            accuracy = 0.4;
        }
        return accuracy;
    }
}
