package com.xyz.mapred.job3;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/** Sample paper as input to Mapper
 *  #*Information geometry of U-Boost and Bregman divergence
	#@NoboruMurata,Takashi Takenouchi,Takafumi Kanamori,Shinto Eguchi
	#t2004
	#cNeural Computation
	#index436405
	%94584
	#%282290
	#%605546
	#%620759
	#%564877
	#%564235
	#%594837
	#%479177
	#%586607
	#!We aim ....
 * @author viswa
 */
public class DBLPMapper extends Mapper<LongWritable, Text, Text, Text> {
	private Text outKey = new Text();
	private Text outValue = new Text();

	@Override
	public void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		final String line = value.toString();
		StringTokenizer tokenizer = new StringTokenizer(line, "\n");
		StringBuilder sb = new StringBuilder();
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (token.startsWith("#index")) {
				outKey.set(token.substring(6, token.length()));
			} else if (token.startsWith("#%")) {
				if (sb.length() == 0) {
					sb.append(token.substring(2, token.length()));
				} else {
					sb.append(", ");
					sb.append(token.substring(2, token.length()));
				}
			}
		}
		
		outValue.set(sb.toString());
		context.write(outKey, outValue);
	}
}
