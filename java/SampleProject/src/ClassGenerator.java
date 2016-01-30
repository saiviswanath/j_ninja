import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class ClassGenerator {

  public static void main(String[] args) {
    String parentPath = "C:\\Users\\viswa\\Downloads\\Sample\\";
    String inputFileName = "sample.txt";
    try (BufferedReader br =
        new BufferedReader(new FileReader(new File(parentPath + inputFileName)))) {
      String line = null;
      StringBuilder sb = new StringBuilder();
      boolean firstLine = true;
      String fileName = null;
      while ((line = br.readLine()) != null) {
        if (firstLine) {
          fileName = line.split("\\s")[1].trim();
          firstLine = false;
        }
        sb.append(line + "\n");
        if (line.length() == 0) {
          createFile(parentPath, fileName, sb.toString());
          sb = new StringBuilder();
          fileName = null;
          firstLine = true;
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void createFile(String parentPath, String fileName, String fileContent) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(parentPath + fileName + ".txt")))) {
      bw.write(fileContent);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

}
