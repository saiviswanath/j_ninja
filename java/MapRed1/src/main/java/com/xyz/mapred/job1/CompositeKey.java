package com.xyz.mapred.job1;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

@SuppressWarnings("rawtypes")
public class CompositeKey implements WritableComparable {
  private String naturalKey;
  private Integer secondaryKey;

  public CompositeKey(String naturalKey, Integer secondaryKey) {
    super();
    this.naturalKey = naturalKey;
    this.secondaryKey = secondaryKey;
  }

  @Override
  public void readFields(DataInput in) throws IOException {
    this.naturalKey = in.readUTF();
    this.secondaryKey = in.readInt();
  }

  @Override
  public void write(DataOutput out) throws IOException {
    out.writeUTF(this.naturalKey);
    out.writeInt(this.secondaryKey);
  }

  @Override
  public int compareTo(Object o) {
    CompositeKey comp = (CompositeKey) o;
    int result = this.naturalKey.compareTo(comp.naturalKey);
    if (result == 0) {
      return this.secondaryKey.compareTo(comp.secondaryKey);
    }
    return result;
  }

  public String getNaturalKey() {
    return naturalKey;
  }

  public void setNaturalKey(String naturalKey) {
    this.naturalKey = naturalKey;
  }

  public Integer getSecondaryKey() {
    return secondaryKey;
  }

  public void setSecondaryKey(Integer secondaryKey) {
    this.secondaryKey = secondaryKey;
  }
}
