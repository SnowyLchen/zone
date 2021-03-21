//package com.cjl.basci.zone.common.utils;
//
//import net.coobird.thumbnailator.Thumbnails;
//import net.coobird.thumbnailator.geometry.Positions;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//
//
//public class ImageUtil {
//
//
//    public static void createMinImg(String fromImg, String toImg, int width, int height) {
//        try {
//            Thumbnails.of(fromImg)
//                    .size(width, height)
//                    .outputQuality(1.0)
//                    .toFile(toImg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static BufferedImage createMinIoImg(InputStream fromImg, int width, int height) {
//        try {
//            return Thumbnails.of(fromImg)
//                    .size(width, height)
//                    .outputQuality(1.0)
//                    .asBufferedImage();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public static void createMinImgWatermark(String fromImg, String toImg, int width, int height, String watermark) {
//        try {
//            Thumbnails.of(fromImg)
//                    .size(width, height)
//                    .outputQuality(1.0)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermark)), 0.5f)
//                    .toFile(toImg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    public static void createImgWatermark(String fromImg, String toImg, String watermark) {
//        try {
//            Thumbnails.of(fromImg)
//                    .scale(1)
//                    .outputQuality(1.0)
//                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(watermark)), 0.5f)
//                    .toFile(toImg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//}
