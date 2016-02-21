package com.xyz.mapred.job1;

import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

public class SortingComparator extends WritableComparator {
  
  public SortingComparator() {
    super(CompositeKey.class, true);
  }

  @SuppressWarnings("rawtypes")
  @Override
  public int compare(WritableComparable a, WritableComparable b) {
    CompositeKey key1 = (CompositeKey) a;
    CompositeKey key2 = (CompositeKey) b;

    int cmp = key1.getNaturalKey().compareTo(key2.getNaturalKey());
    if (cmp == 0) {
      return key1.getSecondaryKey().compareTo(key2.getSecondaryKey());
    }
    return cmp;
  }

}
