package com.xyz.misc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GradleDepenToMaven {
	public static void main(String... args) {
		StringBuilder sb = new StringBuilder();
		sb.append("<dependencies>\n");
		try (BufferedReader br = new BufferedReader(
				new FileReader(new File("E:\\STRUTS_WS\\support\\auto.txt")))) {
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(convert(line)).append("\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		sb.append("</dependencies>");
		System.out.println(sb.toString());
	}

	/**
	 * log4j : [group: "log4j", name: "log4j", version: "1.2.17"],
	 * 
	 * @param line
	 * @return
	 */
	private static String convert(String line) {
		String freshLine1 = line.split("\\[")[1];
		String freshLine2 = freshLine1.split("\\]")[0];

		String[] freshLineToSplit = freshLine2.split(",");
		int count = 0;
		StringBuilder sb = new StringBuilder();
		sb.append("<dependency>\n");
		for (String str : freshLineToSplit) {
			String part = str.trim().split("\"")[1];
			if (count == 0) {
				sb.append("<groupId>").append(part).append("</groupId>\n");
				count++;
				continue;
			} else if (count == 1) {
				sb.append("<artifactId>").append(part).append("</artifactId>\n");
				count++;
				continue;
			} else if (count == 2) {
				sb.append("<version>").append(part).append("</version>\n");
				count++;
				continue;
			} else {
				throw new RuntimeException("Unexpected processing");
			}
		}
		sb.append("</dependency>");
		return sb.toString();
	}
}
