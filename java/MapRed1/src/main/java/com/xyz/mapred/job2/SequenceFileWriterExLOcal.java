package com.xyz.mapred.job2;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.SequenceFile.Writer.Option;
import org.apache.hadoop.io.Text;

public class SequenceFileWriterExLOcal {
  public static void main(String[] args) throws IOException {
    if (args.length < 2) {
      System.out.println("Usage:");
      return;
    }

    SequenceFileWriterExLOcal seqFileWriter = new SequenceFileWriterExLOcal();
    seqFileWriter.writeSequenceFile(args[0], args[1]);
  }

  private void writeSequenceFile(String docDirectoryPath, String sequenceFilePath)
      throws IOException {
    Configuration conf = new Configuration();
    File docDirectory = new File(docDirectoryPath);
    if (!docDirectory.isDirectory()) {
      return;
    }

    Option filePath = SequenceFile.Writer.file(new Path(sequenceFilePath));
    Option keyClass = SequenceFile.Writer.keyClass(Text.class);
    Option valueClass = SequenceFile.Writer.valueClass(BytesWritable.class);
    SequenceFile.Writer sequenceFileWriter =
        SequenceFile.createWriter(conf, filePath, keyClass, valueClass);
    File[] documents = docDirectory.listFiles();
    try {
      for (File document : documents) {
        RandomAccessFile raf = new RandomAccessFile(document, "r");
        byte[] content = new byte[(int) raf.length()];
        raf.readFully(content);
        sequenceFileWriter.append(new Text(document.getName()), new BytesWritable(content));
        raf.close();
      }
    } finally {
      IOUtils.closeStream(sequenceFileWriter);
    }
  }
}
