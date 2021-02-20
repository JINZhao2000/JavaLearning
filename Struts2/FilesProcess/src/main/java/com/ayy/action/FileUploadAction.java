package com.ayy.action;

import com.opensymphony.xwork2.Action;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 20/02/2021
 * @ Version 1.0
 */
public class FileUploadAction {
    private File file;
    private String fileFileName;
    private String fileContentType;

    public String upload(){
        String path = ServletActionContext.getServletContext().getRealPath("/upload");
        try {
            FileUtils.copyFile(file,new File(path,fileFileName));
            return Action.SUCCESS;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Action.ERROR;
    }

    public String toUpload(){
        return Action.SUCCESS;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFileName() {
        return fileFileName;
    }

    public void setFileFileName(String fileFileName) {
        this.fileFileName = fileFileName;
    }

    public String getFileContentType() {
        return fileContentType;
    }

    public void setFileContentType(String fileContentType) {
        this.fileContentType = fileContentType;
    }
}
