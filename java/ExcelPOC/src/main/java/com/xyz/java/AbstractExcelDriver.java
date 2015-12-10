package com.xyz.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExcelDriver {
  protected Workbook workBook;

  public void buildExcel(Workbook workBook, File file) throws IOException, FileNotFoundException {
    this.workBook = workBook;
    processExcelSheet();
    FileOutputStream out = new FileOutputStream(file);
    workBook.write(out);
    out.close();
  }

  protected abstract void processExcelSheet();
}
