package com.xyz.crudservice.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtils {
  public static final int CHAR_WIDTH = 256;

  public static void makeRegion(HSSFSheet sheet, int cnt, HSSFCellStyle cellstyle, String value) {

    // create region for category
    HSSFRow row = sheet.createRow(cnt);
    sheet.addMergedRegion(new CellRangeAddress(cnt, 0, cnt, 12));
    HSSFCell cell = row.createCell(0);
    cell.setCellStyle(cellstyle);
    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
    cell.setCellValue(new HSSFRichTextString(value));
  }

  public static HSSFFont makeFont(HSSFWorkbook workbook, short fontcolor, boolean isbold,
      int fontsize, String fontname) {

    HSSFFont font = workbook.createFont();

    font.setColor(fontcolor);
    if (isbold)
      font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

    font.setFontHeightInPoints((short) fontsize);

    if (fontname.equalsIgnoreCase(""))
      font.setFontName("Arial");
    else
      font.setFontName(fontname);

    return font;

  }

  public static HSSFCellStyle makeStyle(HSSFWorkbook workbook, HSSFFont font, boolean iscolor,
      short stylecolor, short alignment, String formatstring, boolean isborder,
      boolean isrightborder) {

    HSSFDataFormat format = workbook.createDataFormat();

    HSSFCellStyle cellStyle = workbook.createCellStyle();

    cellStyle.setFont(font);

    if (iscolor) {
      cellStyle.setFillForegroundColor(stylecolor);
      cellStyle.setFillBackgroundColor(stylecolor);
      cellStyle.setFillPattern(stylecolor);
    }

    cellStyle.setAlignment(alignment);
    cellStyle.setDataFormat(format.getFormat(formatstring));

    if (isborder) {
      cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
      cellStyle.setBottomBorderColor(HSSFColor.GREY_50_PERCENT.index);
      cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
      cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
      if (isrightborder) {
        cellStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
        cellStyle.setTopBorderColor(HSSFColor.GREY_50_PERCENT.index);
      }
    }

    return cellStyle;
  }

  public static void createFooter(HSSFWorkbook workbook, HSSFSheet sheet, int row,
      String extraMessage) {
    HSSFCellStyle footerStyle = createFooterStyle(workbook);

    int rowNum = row;
    if (extraMessage != null && extraMessage != "") {
      HSSFRow row_footer = sheet.createRow(rowNum++);
      HSSFCell cell_footer = row_footer.createCell(0);
      cell_footer.setCellStyle(footerStyle);
      cell_footer.setCellValue(new HSSFRichTextString(extraMessage));
    }

    HSSFRow row_footer = sheet.createRow(rowNum++);
    HSSFCell cell_footer = row_footer.createCell(0);
    cell_footer.setCellStyle(footerStyle);
    cell_footer.setCellValue(new HSSFRichTextString("Copyright \u00A9 "
        + new SimpleDateFormat("yyyy").format(new Date())));
  }

  private static HSSFCellStyle createFooterStyle(HSSFWorkbook workbook) {
    HSSFCellStyle footerStyle = workbook.createCellStyle();
    HSSFFont font = makeFont(workbook, HSSFColor.GREY_80_PERCENT.index, false, 8, "Arial");
    font.setItalic(true);
    footerStyle.setFont(font);
    return footerStyle;
  }

  public static void addCell(HSSFRow row, HSSFCellStyle cellStyle_col, int colCnt, int cellType,
      Object value) {
    HSSFCell cell = row.createCell(colCnt);
    cell.setCellStyle(cellStyle_col);
    cell.setCellType(cellType);
    if (value instanceof Number) {
      cell.setCellValue(((Number) value).doubleValue());
    } else {
      String stringValue = value.toString();
      if (stringValue.length() > SpreadsheetVersion.EXCEL2007.getMaxTextLength()) {
        stringValue =
            stringValue.substring(0, SpreadsheetVersion.EXCEL2007.getMaxTextLength() - 3) + "...";
      }
      cell.setCellValue(new HSSFRichTextString(stringValue));
    }
  }
}
