package com.xyz.mapred.job1;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * One reducer.
 * 
 * @author viswa
 *
 */
public class TwoWordCounterReducer extends Reducer<CompositeKey, IntWritable, Text, IntWritable> {

  @Override
  protected void reduce(CompositeKey key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
    context.write(new Text(key.getNaturalKey()), new IntWritable(key.getSecondaryKey()));
  }
}
