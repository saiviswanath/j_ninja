package com.xyz.java.utils;

import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.WorkbookUtil;

public class ExcelUtils {

  public static Font setFont(Workbook workBook, short fontColor, boolean isBold, int fontSize,
      String fontName) {
    Font font = workBook.createFont();

    font.setColor(fontColor);
    if (isBold)
      font.setBoldweight(Font.BOLDWEIGHT_BOLD);

    font.setFontHeightInPoints((short) fontSize);

    if (fontName.equalsIgnoreCase(""))
      font.setFontName("Arial");
    else
      font.setFontName(fontName);

    return font;
  }

  public static CellStyle setStyle(Workbook workBook, Font font, boolean isColor, short styleColor,
      short alignment, String formatString, boolean isBorder, boolean isRightBorder) {

    DataFormat format = workBook.createDataFormat();
    CellStyle cellStyle = workBook.createCellStyle();

    cellStyle.setFont(font);
    if (isColor) {
      cellStyle.setFillForegroundColor(styleColor);
      cellStyle.setFillBackgroundColor(styleColor);
      cellStyle.setFillPattern(styleColor);
    }

    cellStyle.setAlignment(alignment);
    cellStyle.setDataFormat(format.getFormat(formatString));

    if (isBorder) {
      cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
      cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
      cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
      cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
      if (isRightBorder) {
        cellStyle.setBorderTop(CellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
      }
    }

    return cellStyle;
  }

  public static Sheet buildSheet(Workbook workBook, String sheetName) {
    String safeName = WorkbookUtil.createSafeSheetName(sheetName);
    return workBook.createSheet(safeName);
  }

  public static Sheet[] createSheets(Workbook workBook, String... sheetNameArray) {
    Sheet[] sheetArray = new Sheet[sheetNameArray.length];
    for (int i = 0; i < sheetNameArray.length; i++) {
      String safeName = WorkbookUtil.createSafeSheetName(sheetNameArray[i]);
      sheetArray[i] = workBook.createSheet(safeName);
    }
    return sheetArray;
  }

  public static void addCell(Row row, CellStyle cellStyle, short colCnt, int cellType, Object value) {
    Cell cell = row.createCell(colCnt);
    cell.setCellStyle(cellStyle);
    cell.setCellType(cellType);
    if (value instanceof Double) {
      cell.setCellValue(((Double) value).doubleValue());
    } else {
      cell.setCellValue(new XSSFRichTextString(value.toString()));
    }
  }

}
