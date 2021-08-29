package cyou.zhaojin.comparable.mapper;

import cyou.zhaojin.comparable.bean.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/08/2021
 * @ Version 1.0
 */

public class FlowMapper extends Mapper<LongWritable, Text, FlowBean, Text> {
    private final Text phoneNum = new Text();
    private final FlowBean fb = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] texts = value.toString().split("\t");
        phoneNum.set(texts[0]);
        fb.setUpFlow(Long.parseLong(texts[1]));
        fb.setDownFlow(Long.parseLong(texts[2]));
        fb.setSumFlow();
        context.write(fb, phoneNum);
    }
}
