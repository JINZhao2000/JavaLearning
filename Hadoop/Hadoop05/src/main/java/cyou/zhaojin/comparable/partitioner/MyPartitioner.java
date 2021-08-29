package cyou.zhaojin.comparable.partitioner;

import cyou.zhaojin.comparable.bean.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 27/08/2021
 * @ Version 1.0
 */

public class MyPartitioner extends Partitioner<FlowBean, Text> {
    @Override
    public int getPartition(FlowBean flowBean, Text text, int numPartitions) {
        String number = text.toString();
        String pre = number.substring(0, 3);
        switch (pre) {
            case "136" :
                return 0;
            case "137" :
                return 1;
            case "138" :
                return 2;
            case "139" :
                return 3;
            default :
                return 4;
        }
    }
}
