package cyou.zhaojin.comparable.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 19/08/2021
 * @ Version 1.0
 */
@Data
@NoArgsConstructor
public class FlowBean implements WritableComparable<FlowBean> {
    private long upFlow;
    private long downFlow;
    private long sumFlow;

    public void setSumFlow() {
        this.sumFlow = this.upFlow + this.downFlow;
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }

    @Override
    public String toString() {
        return upFlow + "\t" + downFlow + "\t" + sumFlow;
    }

    @Override
    public int compareTo(FlowBean o) {
        if (o.sumFlow < this.sumFlow) {
            return -1;
        }
        if (o.sumFlow > this.sumFlow) {
            return 1;
        }
        if (o.upFlow < this.upFlow) {
            return -1;
        }
        if (o.upFlow > this.upFlow) {
            return 1;
        }
        return Long.compare(o.downFlow, this.downFlow);
    }
}
