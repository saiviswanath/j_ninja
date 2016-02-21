package com.xyz.mapred.job1;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class TwoWordCounterPartitioner extends Partitioner<CompositeKey, NullWritable> {

  @Override
  public int getPartition(CompositeKey key, NullWritable val, int numReducers) {
    return key.getNaturalKey().hashCode() % numReducers;
  }

}
