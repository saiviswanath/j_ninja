package com.xyz.mapred.job3;

import java.io.File;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author viswa
 *
 */
public class Driver extends Configured implements Tool {
	private static Logger logger = Logger.getLogger(Driver.class);

	public static void main(String[] args) {
		logger.info("Starting Driver...");

		if (args.length != 1) {
			System.err
					.println("Usage: Driver -DAPP_ROOT=<app_root> <input path>");
			System.exit(-1);
		}

		try {
			System.exit(ToolRunner.run(new Configuration(), new Driver(), args));
		} catch (Exception e) {
			logger.error("Job failed:  ", e);
			System.exit(-1);
		}
	}

	@Override
	public int run(String[] args) throws Exception {
		final Configuration config = getConf();

		final String rootDir = config.get("APP_ROOT");
		PropertyConfigurator.configure(rootDir + File.pathSeparator
				+ "log4j.xml");

		final String localInputPath = args[0];
		final String outputPathDir = "/user/viswa/DBLP-"
				+ System.currentTimeMillis();
		
		// https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-common/DeprecatedProperties.html
		config.set("mapreduce.output.textoutputformat.separator", ",");

		// Job Conf
		Job job = new Job(config);
		job.setJarByClass(Driver.class);
		job.setJobName("DBLP Job");

		FileInputFormat.addInputPath(job, new Path(localInputPath));
		FileOutputFormat.setOutputPath(job, new Path(outputPathDir));

		job.setMapperClass(DBLPMapper.class);

		job.setInputFormatClass(DBLPInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);

		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);

		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);

		if (job.waitForCompletion(true)) {
			return 0;
		} else {
			return -1;
		}
	}
}
