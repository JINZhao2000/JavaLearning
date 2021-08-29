package cyou.zhaojin.comparable.reducer;

import cyou.zhaojin.comparable.bean.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/08/2021
 * @ Version 1.0
 */

public class FlowReducer extends Reducer<FlowBean, Text, Text, FlowBean> {
    @Override
    protected void reduce(FlowBean key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value, key);
        }
    }
}
