package com.xyz.mapred.job1;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.mapreduce.Partitioner;

public class TwoWordCounterPartitioner extends Partitioner<CompositeKey, IntWritable> {

  @Override
  public int getPartition(CompositeKey key, IntWritable val, int numReducers) {
    return key.getNaturalKey().hashCode() % numReducers;
  }

}
