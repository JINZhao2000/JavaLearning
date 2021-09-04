package cyou.zhaojin.reducejoin.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/09/2021
 * @ Version 1.0
 */
@Data
@NoArgsConstructor
public class TableBean implements Writable {
    private String orderId;
    private String pId;
    private int qte;
    private String pName;
    private String flag;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(orderId);
        out.writeUTF(pId);
        out.writeInt(qte);
        out.writeUTF(pName);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.orderId = in.readUTF();
        this.pId = in.readUTF();
        this.qte = in.readInt();
        this.pName = in.readUTF();
        this.flag = in.readUTF();
    }

    @Override
    public String toString() {
        return this.orderId + "\t" + this.pName + "\t" + qte;
    }
}
