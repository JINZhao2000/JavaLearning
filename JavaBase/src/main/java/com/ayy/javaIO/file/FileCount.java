package com.ayy.javaIO.file;

import java.io.File;

/**
 * @ ClassName FileCount
 * @ Description count the number of files
 * @ Author Zhao JIN
 * @ Date 30/10/2020 14:17
 * @ Version 1.0
 */
public class FileCount {
    private long len;
    private String path;
    private File src;
    private int numFile = -1;
    private int numDir;

    public FileCount (String path){
        this.path = path;
        this.src = new File(path);
        count(this.src);
    }

    @Override
    public String toString () {
        return "length : "+this.len+" ,files : "+this.numFile+" ,dirs : "+this.numDir;
    }

    private void count (File src) {
        if (null != src && src.exists()) {
            if (src.isFile()) {
                len += src.length();
                numFile++;
            } else {
                numDir++;
                for (File f1 : src.listFiles()) {
                    count(f1);
                }
            }
        }
    }
}
