package com.ayy.javaIO.randomAccessFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * @ ClassName RandomAccessFile
 * @ Description encapsulation of copy by RandomAccessFile
 * @ Author Zhao JIN
 * @ Date 30/10/2020 15:36
 * @ Version 1.0
 */
public class RandomAccessFile {
    private File src;
    private String destDir;
    private List<String> destPaths;
    private int blockSize;
    private int size;

    public RandomAccessFile (String srcPath, String destDir, int blockSize) {
        this.src = new File(srcPath);
        this.destDir = destDir;
        this.blockSize = blockSize;
        this.destPaths = new ArrayList<>();
        init();
    }

    public RandomAccessFile (String srcPath, String destDir) {
        this(srcPath, destDir, 1024);
    }

    private void init () {
        long len = this.src.length();
        this.size = (int) Math.ceil(len * 1.0 / blockSize);
        for (int i = 0; i < size; i++) {
            this.destPaths.add(this.destDir + "/" + i + "-" + this.src.getName()+".txt");
        }
    }

    public void split () {
        long len = this.src.length();
        int offset = 0;
        int actualSize = (int) (blockSize > len ? len : blockSize);
        for (int i = 0; i < size; i++) {
            offset = i * blockSize;
            if (i == size - 1) {
                actualSize = (int) len;
            } else {
                actualSize = blockSize;
                len -= actualSize;
            }
            System.out.println(i + "-" + offset + "-" + actualSize);
            splitDetail(i, offset, actualSize);
        }
    }

    public void merge (String destPath) {
        try (OutputStream os = new BufferedOutputStream(new FileOutputStream(destPath, true));){
            Vector<InputStream> vi = new Vector<>();
            SequenceInputStream sis;
            for (int i = 0; i < destPaths.size(); i++) {
                vi.add(new BufferedInputStream(new FileInputStream(destPaths.get(i))));
            }
            sis = new SequenceInputStream(vi.elements());
            byte[] flush = new byte[50];
            int len = -1;
            while ((len = sis.read(flush)) != -1) {
                os.write(flush,0,len);
            }
            os.flush();
            sis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void splitDetail (int i, int offset, int actualSize) {
        try (java.io.RandomAccessFile raf = new java.io.RandomAccessFile(this.src, "r");
             java.io.RandomAccessFile raf2 = new java.io.RandomAccessFile(this.destPaths.get(i), "rw")) {
            raf.seek(offset);//beginning offset
            byte[] flush = new byte[50];
            int len = -1;
            while ((len = raf.read(flush)) != -1) {
                if (actualSize > len) {
                    raf2.write(flush, 0, len);
                    actualSize -= len;
                } else {
                    raf2.write(flush, 0, actualSize);
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
