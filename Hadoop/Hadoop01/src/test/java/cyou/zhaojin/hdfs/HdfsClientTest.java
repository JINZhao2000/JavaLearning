package cyou.zhaojin.hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;

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
        fs.copyFromLocalFile(false, false,
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
