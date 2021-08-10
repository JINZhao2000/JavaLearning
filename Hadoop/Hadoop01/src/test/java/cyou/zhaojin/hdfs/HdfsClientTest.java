package cyou.zhaojin.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 09/08/2021
 * @ Version 1.0
 */

public class HdfsClientTest {
    private FileSystem fs = null;

    @Test
    public void mkdirTest() throws Exception {
        fs.mkdirs(new Path("/mkdirTest"));
    }

    @Test
    public void uploadTest() throws Exception {
        // delete Y/N - overwrite Y/N - src Path - dest Path
        fs.copyFromLocalFile(false, true,
                new Path(HdfsClientTest.class.getClassLoader().getResource("log4j.properties").toURI().toString()),
                new Path("hdfs://hadoop01/uploaded/log4j.properties"));
    }

    @Test
    public void downloadTest() throws Exception {
        // delete Y/N - src Path - dest Path - active crc ?
        Path dest = new Path(HdfsClientTest.class.getClassLoader().getResource("log4j.properties").toURI().toString()).getParent().getParent();
        fs.copyToLocalFile(false,
                new Path("hdfs://hadoop01/uploaded/log4j.properties"),
                dest, false);
        System.out.println(dest.toString());
    }

    @Test
    public void testRm() throws Exception {
        // path - recursive (rm -r)
        fs.delete(new Path("hdfs://hadoop01/output"), true);
    }

    @Test
    public void testMv() throws Exception {
        // src Path - dest Path
        fs.rename(new Path("/input/word.txt"), new Path("/input/word2.txt"));
    }

    @Test
    public void testFileDetail() throws Exception {
        // path - recursive
        RemoteIterator<LocatedFileStatus> files = fs.listFiles(new Path("/tmp/"), true);
        while (files.hasNext()) {
            LocatedFileStatus next = files.next();
            System.out.println(next.getPath().toString());
            System.out.println(next.getPermission().toString());
            System.out.println(next.getOwner());
            System.out.println(next.getGroup());
            System.out.println(next.getLen());
            System.out.println(next.getModificationTime());
            System.out.println(next.getReplication());
            System.out.println(next.getBlockSize());
            System.out.println(Arrays.toString(next.getBlockLocations()));
            System.out.println(next.getPath().getName());
            System.out.println("==================");
        }
    }

    @Test
    public void testFileDirectory() throws Exception {
        FileStatus[] fileStatuses = fs.listStatus(new Path("/"));
        for (FileStatus fileStatus : fileStatuses) {
            if (fileStatus.isFile()) {
                System.out.println(fileStatus.getPath().getName() + " is a file");
            } else {
                System.out.println(fileStatus.getPath().getName() + " is a directory");
            }
        }
    }

    /**
     * priority :
     *  default-hdfs < hdfs-site (server) < hdfs-site (/resources/hdfs-site.xml) < Configuration
     */
    @Before
    public void init(){
        try {
            URI uri = new URI("hdfs://hadoop01:8020");

            Configuration cfg = new Configuration();
            String user = "root";

            fs = FileSystem.get(uri, cfg, user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new AssertionError("Init Error");
        }
    }

    @After
    public void close(){
        if (fs != null) {
            try {
                fs.close();
                fs = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
