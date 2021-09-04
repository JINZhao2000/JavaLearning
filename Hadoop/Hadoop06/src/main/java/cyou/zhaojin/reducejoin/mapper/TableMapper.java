package cyou.zhaojin.reducejoin.mapper;

import cyou.zhaojin.reducejoin.bean.TableBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 04/09/2021
 * @ Version 1.0
 */

public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    private String fileName;
    private Text text = new Text();
    private TableBean tableBean = new TableBean();

    @Override
    protected void setup(Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
        FileSplit fileSplit = (FileSplit) context.getInputSplit();
        fileName = fileSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, TableBean>.Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] split = line.split(" ");
        if (fileName.contains("order")) {
            text.set(split[1]);
            tableBean.setOrderId(split[0]);
            tableBean.setPId(split[1]);
            tableBean.setQte(Integer.parseInt(split[2]));
            tableBean.setPName("");
            tableBean.setFlag("order");
        } else {
            text.set(split[0]);
            tableBean.setOrderId("");
            tableBean.setPId(split[0]);
            tableBean.setQte(0);
            tableBean.setPName(split[1]);
            tableBean.setFlag("pd");
        }
        context.write(text, tableBean);
    }
}
