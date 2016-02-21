package com.xyz.mapred.job1;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.MapReduceDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;
import org.junit.Before;
import org.junit.Test;

public class TwoWordCounterTest {
  MapDriver<LongWritable, Text, Text, IntWritable> mapDriver;
  ReduceDriver<Text, IntWritable, Text, IntWritable> reduceDriver;
  MapReduceDriver<LongWritable, Text, Text, IntWritable, Text, IntWritable> mapReduceDriver;

  @Before
  public void setUp() {
    TwoWordCounterMapper mapper = new TwoWordCounterMapper();
    TwoWordCounterReducer reducer = new TwoWordCounterReducer();
    mapDriver = MapDriver.newMapDriver(mapper);
    reduceDriver = ReduceDriver.newReduceDriver(reducer);
    mapReduceDriver = MapReduceDriver.newMapReduceDriver(mapper, reducer);
  }

  @Test
  public void testMapper() throws Exception {
    mapDriver.withInput(new LongWritable(), new Text(
        "All worlds is a stage; And all, people are actors sigh!"));
    mapDriver.withOutput(new Text("All#worlds"), new IntWritable(1));
    mapDriver.withOutput(new Text("is#a"), new IntWritable(1));
    mapDriver.withOutput(new Text("stage#And"), new IntWritable(1));
    mapDriver.withOutput(new Text("all#people"), new IntWritable(1));
    mapDriver.withOutput(new Text("are#actors"), new IntWritable(1));
    mapDriver.withOutput(new Text("sigh!#END"), new IntWritable(1));
    mapDriver.runTest();
  }

  @Test
  public void testReducer() throws Exception {
    List<IntWritable> values = new ArrayList<IntWritable>();
    values.add(new IntWritable(2));
    values.add(new IntWritable(1));
    reduceDriver.withInput(new Text("All#worlds"), values);
    reduceDriver.withOutput(new Text("All#worlds"), new IntWritable(3));
    reduceDriver.runTest();
  }
}
