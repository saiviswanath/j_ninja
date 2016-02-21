package com.xyz.mapred.job1;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * Map Key is dummy. As Anything else throwing ClassCast.
 * 
 * @author viswa
 *
 */
public class TwoWordCounterMapper extends Mapper<LongWritable, Text, CompositeKey, NullWritable> {
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
      String token1 = lineTokens.nextToken();
      CompositeKey inKey = null;
      try {
        String token2 = lineTokens.nextToken();
        inKey = new CompositeKey(token1 + "#" + token2, 1);
      } catch (NoSuchElementException e) {
        inKey = new CompositeKey(token1 + "#END", 1);
      }
      context.write(inKey, NullWritable.get());
    }
  }
}
