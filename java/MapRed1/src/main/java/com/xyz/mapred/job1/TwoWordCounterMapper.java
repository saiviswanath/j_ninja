package com.xyz.mapred.job1;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Map Key is dummy. As Anything else throwing ClassCast.
 * 
 * @author viswa
 *
 */
public class TwoWordCounterMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
  @Override
  protected void map(LongWritable key, Text value, Context context) throws IOException,
      InterruptedException {
    final String line = value.toString();

    if (line.isEmpty()) {
      return;
    }

    StringTokenizer lineTokens = new StringTokenizer(line, " ;,"); // TODO: Define all possible
                                                                 // delimiters in a doc
    while (lineTokens.hasMoreTokens()) {
      Text inKey = null;
      String token1 = lineTokens.nextToken();
      try {
        String token2 = lineTokens.nextToken();
        inKey = new Text(token1 + "#" + token2);
      } catch (NoSuchElementException e) {
        inKey = new Text(token1 + "#" + "END");
      }
      context.write(inKey, new IntWritable(1));
    }
  }
}
