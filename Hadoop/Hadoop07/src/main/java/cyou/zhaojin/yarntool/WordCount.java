package cyou.zhaojin.yarntool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;

import java.io.IOException;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 18/09/2021
 * @ Version 1.0
 */

public class WordCount implements Tool{
    private Configuration configuration;

    @Override
    public int run(String[] args) throws Exception {
        Job job = Job.getInstance(configuration);
        job.setJarByClass(WordCountDriver.class);
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.setInputPaths(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        return job.waitForCompletion(true) ? 0 : 1;
    }

    @Override
    public void setConf(Configuration conf) {
        this.configuration = conf;
    }

    @Override
    public Configuration getConf() {
        return this.configuration;
    }

    public static class WordCountMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
        private final Text text = new Text();
        private final IntWritable intWritable = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            String[] splits = value.toString().split(" ");
            for (String split : splits) {
                text.set(split);
                context.write(text, intWritable);
            }
        }
    }

    public static class WordCountReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
        private final Text text = new Text();
        private final IntWritable intWritable = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> values, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable value : values) {
                sum += value.get();
            }
            text.set(key);
            intWritable.set(sum);
            context.write(key, intWritable);
        }
    }
}
