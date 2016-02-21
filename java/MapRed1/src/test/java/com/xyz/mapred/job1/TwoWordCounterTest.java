package com.xyz.mapred.job1;

import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.mapreduce.ReduceDriver;

public class TwoWordCounterTest {
  MapDriver<LongWritable, Text, CompositeKey, NullWritable> mapDriver;
  ReduceDriver<CompositeKey, NullWritable, CompositeKey, NullWritable> reduceDriver;

  //@Before
  public void setUp() {
    TwoWordCounterMapper mapper = new TwoWordCounterMapper();
    TwoWordCounterReducer reducer = new TwoWordCounterReducer();
    mapDriver = MapDriver.newMapDriver(mapper);
    reduceDriver = ReduceDriver.newReduceDriver(reducer);
  }

  //@Test
  public void testMapper() throws Exception {
    mapDriver.withInput(new LongWritable(), new Text(
        "All worlds is a stage; And all, people are actors sigh!"));
    mapDriver.withOutput(new CompositeKey("All#worlds", 1), NullWritable.get());
    mapDriver.withOutput(new CompositeKey("is#a", 1), NullWritable.get());
    mapDriver.withOutput(new CompositeKey("stage#And", 1), NullWritable.get());
    mapDriver.withOutput(new CompositeKey("all#people", 1), NullWritable.get());
    mapDriver.withOutput(new CompositeKey("are#actors", 1), NullWritable.get());
    mapDriver.withOutput(new CompositeKey("sigh!#END", 1), NullWritable.get());
    mapDriver.runTest();
  }

  //@Test
  public void testReducer() throws Exception {
    List<NullWritable> values = new ArrayList<NullWritable>();
    values.add(NullWritable.get());

    reduceDriver.withInput(new CompositeKey("All#worlds", 1), values);
    reduceDriver.withOutput(new CompositeKey("All#worlds", 1), NullWritable.get());
    reduceDriver.runTest();
  }
}
