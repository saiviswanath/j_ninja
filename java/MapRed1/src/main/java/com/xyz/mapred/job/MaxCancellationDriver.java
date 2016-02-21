package com.xyz.mapred.job;

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
 * TODO: Test on cloudera VM.
 * 
 * @author viswa
 *
 */
public class MaxCancellationDriver extends Configured implements Tool {
  private static Logger logger = Logger.getLogger(MaxCancellationDriver.class);

  public static void main(String[] args) {
    logger.info("Starting MaxCancellationDriver...");

    if (args.length != 1) {
      System.err
          .println("Usage: MaxCancellationDriver -DAPP_ROOT=<app_root> <input path(s3 url?)>");
      System.exit(-1);
    }

    try {
      System.exit(ToolRunner.run(new Configuration(), new MaxCancellationDriver(), args));
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
    final String outputPathDir = "/tmp/MaxCancellation-" + System.currentTimeMillis();

    config.set("maprededuce.compress.map.output", "true");
    config.set("mapreduce.map.output.compression.codec",
        "org.apache.hadoop.io.compress.SnappyCodec");

    // Job Conf
    Job job = new Job(config);
    job.setJarByClass(MaxCancellationDriver.class);
    job.setJobName("MaxCancellation Job");

    // TODO: Modify per req. How to configure for s3 url and iterate the input dir.
    FileInputFormat.addInputPath(job, new Path(localInputPath));
    FileOutputFormat.setOutputPath(job, new Path(outputPathDir));

    job.setMapperClass(MaxCancellationMapper.class);
    job.setCombinerClass(MaxCancellationReducer.class);
    job.setReducerClass(MaxCancellationReducer.class);

    job.setInputFormatClass(TextInputFormat.class);
    job.setOutputFormatClass(TextOutputFormat.class);

    job.setMapOutputKeyClass(Text.class);
    job.setMapOutputValueClass(IntWritable.class);

    job.setOutputKeyClass(Text.class);
    job.setOutputValueClass(IntWritable.class);

    job.setNumReduceTasks(1);

    if (job.waitForCompletion(true)) {
      Path reducerFile = new Path(outputPathDir + "part-r-000"); // TODO: Validate reducer file
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
