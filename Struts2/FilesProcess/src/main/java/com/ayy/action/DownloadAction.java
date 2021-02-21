package com.ayy.action;

import com.opensymphony.xwork2.Action;
import org.apache.struts2.ServletActionContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 21/02/2021
 * @ Version 1.0
 */
public class DownloadAction {
    private String fileName;

    public String execute(){
        return Action.SUCCESS;
    }

    public InputStream getInputStream(){
        String path = ServletActionContext.getServletContext().getRealPath("/download");
        try {
            return new FileInputStream(new File(path,fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
