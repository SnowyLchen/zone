package com.cjl.basic.zone.common.controller;

import com.cjl.basic.zone.common.utils.StringUtils;
import com.cjl.basic.zone.common.utils.UploadPathUtils;
import com.cjl.basic.zone.framework.ftp.FtpService;
import com.cjl.basic.zone.framework.web.controller.BaseController;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/common")
public class CommonController extends BaseController {

    @Autowired
    private FtpService ftpService;

    /**
     * 上传文件
     */
    @ResponseBody
    @RequestMapping("/upload")
    public AjaxResult upload(HttpServletRequest request, @RequestParam("edit") MultipartFile[] edit) {
        List<Map<String, String>> files = new ArrayList<>();
        for (MultipartFile multipartFile : edit) {
            files.add(UploadPathUtils.thumbnail(multipartFile,ftpService));
        }
        return AjaxResult.success(files);
    }

    /**
     * 下载 服务器图片
     *
     * @param id
     * @param response
     * @throws Exception
     */
    @GetMapping("/downloadPicture/{pictureId}")
    public void downloadPicture(@PathVariable("pictureId") String id, HttpServletResponse response) throws Exception {
        String[] s = StringUtils.split(id, "_");
        String url = "/home/ftp/" + s[1] + "/" + s[0];
        InputStream inputStream = null;
        OutputStream outputStream = null;

        try {
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(id, "UTF-8"));
            inputStream = new FileInputStream(url);
            int length = 0;
            byte[] buffer = new byte[1024];
            outputStream = response.getOutputStream();
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
