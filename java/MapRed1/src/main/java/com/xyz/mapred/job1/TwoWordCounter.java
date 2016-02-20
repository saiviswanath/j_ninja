package com.xyz.mapred.job1;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Map: 1. Treat each new line as a record. 2. Split the line and capture 2 words as one word. 3.
 * And add a counter for 2 per line
 * 
 * Reduce: Aggregate
 * 
 * Comparator: Custom Comparator for descending
 * 
 * @author viswa
 *
 */
public class TwoWordCounter extends Configured implements Tool {
  private static Logger logger = Logger.getLogger(TwoWordCounter.class);

  public static void main(String[] args) {
    logger.info("Starting TwoWordCounter...");

    if (args.length != 1) {
      System.err
          .println("Usage: hadoop jar <JAR> <Main Class FQN> -DAPP_ROOT=<app_root> <input path)>");
      System.exit(-1);
    }

    try {
      System.exit(ToolRunner.run(new Configuration(), new TwoWordCounter(), args));
    } catch (Exception e) {
      logger.error("Job failed:  ", e);
      System.exit(-1);
    }
  }

  @Override
  public int run(String[] args) throws Exception {
    final Configuration config = getConf();
    final FileSystem hdfs = FileSystem.get(config);

    final String rootDir = config.get("APP_ROOT");
    PropertyConfigurator.configure(rootDir + File.pathSeparator + "log4j.xml");

    final String localInputPath = args[0];
    final String outputPathDir = "/tmp/TwoWordCounter-" + System.currentTimeMillis();

    config.set("maprededuce.compress.map.output", "true");
    config.set("mapreduce.map.output.compression.codec",
        "org.apache.hadoop.io.compress.SnappyCodec");

    // Job Conf
    Job job = new Job(config);
    job.setJarByClass(TwoWordCounter.class);
    job.setJobName("TwoWordCounter Job");

    FileInputFormat.addInputPath(job, new Path(localInputPath));
    job.setInputFormatClass(TextInputFormat.class);
    FileOutputFormat.setOutputPath(job, new Path(outputPathDir));
    job.setOutputFormatClass(TextOutputFormat.class);

    job.setMapperClass(TwoWordCounterMapper.class);
    job.setReducerClass(TwoWordCounterReducer.class);

    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    job.setNumReduceTasks(1);

    if (job.waitForCompletion(true)) {
      Path reducerFile = new Path(outputPathDir + "part-r-00000"); // TODO: Validate reducer file
      // name
      long numBytes = hdfs.getFileStatus(reducerFile).getLen();
      if (numBytes > 0) {
        Path outFile =
            new Path(rootDir + File.pathSeparator + "output" + File.pathSeparator + "OutFile.txt");
        hdfs.copyToLocalFile(reducerFile, outFile);
        return 0;
      } else {
        return -1;
      }
    } else {
      return -1;
    }
  }
}
