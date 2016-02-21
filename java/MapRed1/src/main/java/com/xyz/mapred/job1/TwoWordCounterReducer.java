package com.xyz.mapred.job1;

import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * One reducer.
 * 
 * @author viswa
 *
 */
public class TwoWordCounterReducer extends Reducer<Text, IntWritable, Text, IntWritable> {
  private Map<String, Integer> map;

  @Override
  protected void setup(Context context) throws IOException, InterruptedException {
    map = new HashMap<String, Integer>();
  }

  @Override
  protected void reduce(Text key, Iterable<IntWritable> values, Context context)
      throws IOException, InterruptedException {
    int counter = 0;
    for (IntWritable value : values) {
      counter += value.get();
    }
    map.put(key.toString(), counter);
  }

  @Override
  protected void cleanup(Context context) throws IOException, InterruptedException {
    sortByValues(map);
    for (Map.Entry<String, Integer> entry : map.entrySet()) {
      context.write(new Text(entry.getKey()), new IntWritable(entry.getValue()));
    }
  }

  private static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
    Comparator<K> valueComparator = new Comparator<K>() {
      public int compare(K k1, K k2) {
        int compare = map.get(k2).compareTo(map.get(k1));
        if (compare == 0)
          return 1;
        else
          return compare;
      }
    };
    Map<K, V> sortedByValues = new TreeMap<K, V>(valueComparator);
    sortedByValues.putAll(map);
    return sortedByValues;
  }
}
