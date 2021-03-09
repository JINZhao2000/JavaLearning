package com.ayy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 08/03/2021
 * @ Version 1.0
 */
@Controller
public class FileController {
    @RequestMapping("/upload")
    public String upload(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request) {
        String fileName = file.getOriginalFilename();
        if ("".equals(fileName)) {
            return "redirect:/index.jsp";
        }
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if(!realPath.exists()){
            realPath.mkdir();
        }

        try (InputStream is = file.getInputStream();
             OutputStream os = new FileOutputStream(new File(realPath,fileName))) {
            int len = -1;
            byte[] buffer = new byte[1024];
            while((len = is.read(buffer))!=-1){
                os.write(buffer,0,len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/index.jsp";
    }

    @RequestMapping("/upload2")
    public String upload2(@RequestParam("file") CommonsMultipartFile file, HttpServletRequest request){
        String path = request.getServletContext().getRealPath("/upload");
        File realPath = new File(path);
        if(!realPath.exists()){
            realPath.mkdir();
        }
        try {
            file.transferTo(new File(realPath+"/"+file.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/index.jsp";
    }

    @RequestMapping("/download")
    public String download(HttpServletResponse response, HttpServletRequest request) {
        String path = request.getServletContext().getRealPath("/upload");
        String fileName = "....";

        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("multipart/form-data");
        try {
            response.setHeader("Content-Disposition", "attachment;fileName="+ URLEncoder.encode(fileName,"utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        File file = new File(path, fileName);
        try (InputStream is = new FileInputStream(file);
            OutputStream os = response.getOutputStream()){
            byte[] buffer = new byte[1024];
            int len = -1;
            while((len = is.read(buffer))!=-1){
                os.write(buffer,0,len);
            }
            os.flush();
        } catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/index.jsp";
    }
}
