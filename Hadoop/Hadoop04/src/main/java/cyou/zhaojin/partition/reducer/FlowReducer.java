package cyou.zhaojin.partition.reducer;

import cyou.zhaojin.partition.bean.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/08/2021
 * @ Version 1.0
 */

public class FlowReducer extends Reducer<Text, FlowBean, Text, FlowBean> {
    private final FlowBean flowBean = new FlowBean();
    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long upSum = 0;
        long downSum = 0;
        for (FlowBean value : values) {
            upSum += value.getUpFlow();
            downSum += value.getDownFlow();
        }
        flowBean.setUpFlow(upSum);
        flowBean.setDownFlow(downSum);
        flowBean.setSumFlow();
        context.write(key, flowBean);
    }
}
