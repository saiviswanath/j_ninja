package com.xyz.crudservice.export;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

import com.xyz.crudservice.dto.StudentDto;
import com.xyz.crudservice.util.ExcelUtils;

public class StudentExcelExport {
  public static void excelExporter(OutputStream os, List<StudentDto> students) throws IOException {
    // Create workbook

    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Students");
    sheet.setDisplayGridlines(true);

    int rowCount = 0;

    HSSFRow row = sheet.createRow(rowCount);

    // create font and style for title
    HSSFFont font_title = ExcelUtils.makeFont(workbook, HSSFColor.BLACK.index, false, 12, "");
    HSSFCellStyle cellStyle_title = workbook.createCellStyle();
    cellStyle_title.setFont(font_title);

    ExcelUtils.makeRegion(sheet, rowCount, cellStyle_title, "Students List");

    HSSFFont colFont = ExcelUtils.makeFont(workbook, HSSFColor.BLACK.index, true, 10, "");
    rowCount += 2;

    ReportColumn reportColumns[] =
      {new ReportColumn("FirstName", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("LastName", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("Gender", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("DOB", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("Email", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("MobileNumber", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("Address", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT),
        new ReportColumn("Courses", ExcelUtils.CHAR_WIDTH * 18, HSSFCellStyle.ALIGN_LEFT)};

    for (int colIdx = 0; colIdx < reportColumns.length; colIdx++) {
      sheet.setColumnWidth(colIdx, reportColumns[colIdx].getWidth());
    }

    ++rowCount;// Empty Row
    row = sheet.createRow(++rowCount);

    HSSFCellStyle cellStyle_col_header =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_RIGHT, "", false, false);
    HSSFCellStyle leftStyle =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_LEFT, "", false, false);
    HSSFCellStyle rightStyle =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_RIGHT, "", false, false);

    cellStyle_col_header.setWrapText(false);

    for (int i = 0; i < reportColumns.length; i++) {
      HSSFCell cell = row.createCell(i);
      cell.setCellStyle(reportColumns[i].getAlignment() == HSSFCellStyle.ALIGN_LEFT ? leftStyle
          : rightStyle);
      cell.setCellType(HSSFCell.CELL_TYPE_STRING);
      cell.setCellValue(new HSSFRichTextString(reportColumns[i].getLabel()));
    }

    // Style for table data
    colFont = ExcelUtils.makeFont(workbook, HSSFColor.BLACK.index, false, 10, "");

    HSSFCellStyle cellStyle_col =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_RIGHT, "", false, false);
    cellStyle_col.setWrapText(false);
    HSSFCellStyle cellStyle_col_left =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_LEFT, "", false, false);
    HSSFCellStyle cellStyle_col_right =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_RIGHT, "0", false, false);
    HSSFCellStyle cellStyle_col_right_decimal =
        ExcelUtils.makeStyle(workbook, colFont, false, HSSFColor.WHITE.index,
            HSSFCellStyle.ALIGN_RIGHT, "0", false, false);
    HSSFDataFormat dataFormat = workbook.createDataFormat();
    cellStyle_col_right_decimal.setDataFormat(dataFormat.getFormat("0.0"));

    if (students.size() == 0) {
      return;
    }

    for (StudentDto student : students) {
      row = sheet.createRow(++rowCount);
      populateExcelRow(workbook, row, colFont, student, cellStyle_col_left, cellStyle_col_right,
          cellStyle_col_right_decimal);
    }

    rowCount += 3; // 3 blank rows
    ExcelUtils.createFooter(workbook, sheet, rowCount, null);

    // Write the Excel sheet
    workbook.write(os);
    os.flush();

  }

  private static void populateExcelRow(HSSFWorkbook workbook, HSSFRow row, HSSFFont colFont,
      StudentDto student, HSSFCellStyle cellStyle_col_left, HSSFCellStyle cellStyle_col_right,
      HSSFCellStyle cellStyle_col_right_decimal) {
    int colCnt = 0;
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getFirstName());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getLastName());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getGender());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getDOB());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getEmail());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getMobileNumber());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getAddress());
    ExcelUtils.addCell(row, cellStyle_col_left, colCnt++, HSSFCell.CELL_TYPE_STRING,
        student.getCourses());
  }
}
