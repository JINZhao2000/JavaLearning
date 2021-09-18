package cyou.zhaojin.yarntool;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;

/**
 * @ Description
 * @ Author Zhao JIN
 * @ Date 18/09/2021
 * @ Version 1.0
 */

public class WordCountDriver {

    private static Tool tool;

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        if (!"wordcount".equals(args[0])) {
            throw new RuntimeException("no such tool " + args[0]);
        }
        tool = new WordCount();
        int run = ToolRunner.run(configuration, tool, Arrays.copyOfRange(args, 1, args.length));
        System.exit(run);
    }
}
