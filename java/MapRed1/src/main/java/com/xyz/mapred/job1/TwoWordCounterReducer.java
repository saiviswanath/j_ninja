package com.xyz.mapred.job1;

import java.io.IOException;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * One reducer.
 * 
 * @author viswa
 *
 */
public class TwoWordCounterReducer extends
    Reducer<CompositeKey, NullWritable, CompositeKey, NullWritable> {

  @Override
  protected void reduce(CompositeKey key, Iterable<NullWritable> values, Context context)
      throws IOException, InterruptedException {
    for (NullWritable value : values) {
      context.write(key, NullWritable.get());
    }
  }
}
