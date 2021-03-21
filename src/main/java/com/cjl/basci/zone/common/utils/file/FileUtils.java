package com.cjl.basci.zone.common.utils.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件处理工具类
 *
 * @author wangsen
 */
public class FileUtils {
    private static Pattern linePattern = Pattern.compile("_(\\w)");

    /**
     * 输出指定文件的byte数组
     *
     * @param filename 文件
     * @return
     */
    public static void writeBytes(String filePath, OutputStream os) throws IOException {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            throw e;
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 删除文件
     *
     * @param filePath 文件
     * @return
     */
    public static boolean deleteFile(String filePath) {
        boolean flag = false;
        File file = new File(filePath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }

    /**
     * 将下划线文件名转化驼峰
     *
     * @param str 文件名
     * @return 驼峰文件名
     */
    public static String lineToHump(String str) {
        str = str.toLowerCase();
        Matcher matcher = linePattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    // 拷贝文件
    public static void copyFile(String srcPath, String destPath) {

        // 打开输入流
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(srcPath);
            // 打开输出流
            FileOutputStream fos = new FileOutputStream(destPath);

            // 读取和写入信息
            int len = 0;
            // 创建一个字节数组，当做缓冲区
            byte[] b = new byte[1024];
            while ((len = fis.read(b)) != -1) {
                fos.write(b, 0, len);
            }

            // 关闭流  先开后关  后开先关
            fos.close(); // 后开先关
            fis.close(); // 先开后关
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * bufferedImage 转 InputStream
     *
     * @param bufferedImage file文件
     */
    public static InputStream imageToInputStream(BufferedImage bufferedImage, String newName) throws IOException {
        if (newName.equalsIgnoreCase("jpg") || newName.equalsIgnoreCase("jpeg")) {
            //重画一下，要么会变色
            BufferedImage tag;
            tag = new BufferedImage(bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_INT_BGR);
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(bufferedImage, 0, 0, null);
            g.dispose();
            bufferedImage = tag;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ImageIO.write(bufferedImage, newName, bo);
        return new ByteArrayInputStream(bo.toByteArray());
    }
}
