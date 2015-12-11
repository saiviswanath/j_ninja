package com.xyz.hrapp.el;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xyz.java.AbstractExcelDriver;
import com.xyz.java.EmployeeExcelDriver;

public class ExcelEL {

  public static void excelExport(HttpServletResponse resp) throws FileNotFoundException,
  IOException {
    AbstractExcelDriver driver = new EmployeeExcelDriver("EmployeeSheet");
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    driver.buildExcel(bos, new XSSFWorkbook());
    byte[] bytes = bos.toByteArray();
    InputStream is = new ByteArrayInputStream(bytes);
    resp.setContentLength(bytes.length);
    resp.setContentType("application/vnd.ms-excel");
    resp.setHeader("Content-Disposition", "inline; filename=" + "ExportExcel.xls");
    IOUtils.copy(is, bos);
    resp.flushBuffer();
  }
}
