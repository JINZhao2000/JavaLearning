package cyou.zhaojin.partition.mapper;

import cyou.zhaojin.partition.bean.FlowBean;
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

public class FlowMapper extends Mapper<LongWritable, Text, Text, FlowBean> {
    private final Text phoneNum = new Text();
    private final FlowBean fb = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] texts = value.toString().split("\t");
        phoneNum.set(texts[1]);
        fb.setUpFlow(Long.parseLong(texts[4]));
        fb.setDownFlow(Long.parseLong(texts[5]));
        fb.setSumFlow();
        context.write(phoneNum, fb);
    }
}
