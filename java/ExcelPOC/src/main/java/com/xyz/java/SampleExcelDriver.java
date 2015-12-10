package com.xyz.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xyz.java.utils.ExcelUtils;

public class SampleExcelDriver extends AbstractExcelDriver {
  private String sheetName;
  private Map<Integer, Set<String>> data;

  public SampleExcelDriver(String sheetName, Map<Integer, Set<String>> data) {
    this.sheetName = sheetName;
    this.data = data;
  }

  @Override
  public void processExcelSheet() {
    Sheet sheet = ExcelUtils.buildSheet(super.workBook, sheetName);
    sheet.setDisplayGridlines(true);
    for (Map.Entry<Integer, Set<String>> entry : data.entrySet()) {
      Integer rowNum = entry.getKey();
      Set<String> cellVals = entry.getValue();
      Row row = sheet.createRow(rowNum);
      row.setHeightInPoints(30);
      Font font = ExcelUtils.setFont(super.workBook, HSSFColor.BLACK.index, true, 10, "");
      CellStyle cellStyle =
          ExcelUtils.setStyle(super.workBook, font, false, HSSFColor.WHITE.index,
              CellStyle.ALIGN_RIGHT, "", false, false);
      short count = 0;
      for (String val : cellVals) {
        count++;
        ExcelUtils.addCell(row, cellStyle, count, Cell.CELL_TYPE_STRING, val);
      }
    }
  }

  public static void main(String... args) {
    Map<Integer, Set<String>> data = new HashMap<Integer, Set<String>>();
    Set<String> set = new HashSet<String>(Arrays.asList("Sample", "Simple", "Complex"));
    for (int i = 0; i < 5; i++) {
      data.put(i, set);
    }

    AbstractExcelDriver driver = new SampleExcelDriver("SampleSheet", data);
    try {
      driver.buildExcel(new XSSFWorkbook(), new File("E:\\Sample.xlsx"));
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
