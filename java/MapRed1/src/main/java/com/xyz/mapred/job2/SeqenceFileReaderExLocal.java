package com.xyz.mapred.job2;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Reader.Option;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.util.ReflectionUtils;

public class SeqenceFileReaderExLocal {
  public static void main(String[] args) throws IOException {
    if (args.length < 1) {
      System.out.println("Usage:");
      return;
    }

    SeqenceFileReaderExLocal seqFileReader = new SeqenceFileReaderExLocal();
    seqFileReader.readSequenceFile(args[0]);
  }

  private void readSequenceFile(String sequenceFilePath) throws IOException {
    Configuration conf = new Configuration();
    Option filePath = SequenceFile.Reader.file(new Path(sequenceFilePath));
    SequenceFile.Reader sequenceFileReader = new SequenceFile.Reader(conf, filePath);
    Writable key = (Writable) ReflectionUtils.newInstance(sequenceFileReader.getKeyClass(), conf);
    Writable value =
        (Writable) ReflectionUtils.newInstance(sequenceFileReader.getValueClass(), conf);
    try {
      while (sequenceFileReader.next(key, value)) {
        System.out.printf("[%s] %s %s \n", sequenceFileReader.getPosition(), key, value.getClass());
      }
    } finally {
      IOUtils.closeStream(sequenceFileReader);
    }
  }
}
