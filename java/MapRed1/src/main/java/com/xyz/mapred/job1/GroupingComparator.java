package com.xyz.mapred.job1;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class GroupingComparator extends WritableComparator {

  @SuppressWarnings("rawtypes")
  @Override
  public int compare(WritableComparable a, WritableComparable b) {
    CompositeKey key1 = (CompositeKey) a;
    CompositeKey key2 = (CompositeKey) b;

    return key1.getNaturalKey().compareTo(key2.getNaturalKey());
  }
}
