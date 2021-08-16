package cyou.zhaojin.mapred.wordcount;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 16/08/2021
 * @ Version 1.0
 */

public class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
    private Text text = new Text();
    private IntWritable intWritable = new IntWritable(1);
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] words = line.split(" ");

        for (int i = 0; i < words.length; i++) {
            text.set(words[i]);
            context.write(text, intWritable);
        }
    }
}
