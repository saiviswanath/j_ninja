package com.xyz.mapred.job;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * TODO: Does NullWritable work as key while loading map input?
 * 
 * @author viswa
 */
public class MaxCancellationMapper extends Mapper<NullWritable, Text, Text, IntWritable> {

  @Override
  public void map(NullWritable key, Text value, Context context) throws IOException,
      InterruptedException {
    String line = value.toString();

    if (line.isEmpty() || line.startsWith("Year")) {
      return;
    }

    String[] fieldVals = line.split(",");

    StringBuilder sb = new StringBuilder();
    sb.append(fieldVals[8]).append("\\t").append(fieldVals[16]).append(",").append(fieldVals[17]);


    IntWritable outVal = null;
    boolean cancelFlag = Boolean.parseBoolean(fieldVals[21]);
    if (cancelFlag) {
      outVal = new IntWritable(1);
    } else {
      outVal = new IntWritable(0);
    }

    context.write(new Text(sb.toString()), outVal);
  }
}
