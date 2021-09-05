package cyou.zhaojin.mapjoin.mapper;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/09/2021
 * @ Version 1.0
 */

public class TableMapper extends Mapper<LongWritable, Text, Text, NullWritable> {
    private Map<String, String> pdMap = new HashMap<>();
    private Text text = new Text();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        URI cacheFile = context.getCacheFiles()[0];
        FileSystem fileSystem = FileSystem.get(context.getConfiguration());
        FSDataInputStream open = fileSystem.open(new Path(cacheFile));
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(open, StandardCharsets.UTF_8));
        String msg = null;
        while (StringUtils.isNotEmpty(msg = bufferedReader.readLine())){
            String[] s = msg.split(" ");
            pdMap.put(s[0], s[1]);
        }
        IOUtils.closeStream(bufferedReader);
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, NullWritable>.Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(" ");
        String pName = pdMap.get(fields[1]);
        text.set(fields[0]+"\t"+pName+"\t"+fields[2]);
        context.write(text, NullWritable.get());
    }
}
