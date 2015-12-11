package com.xyz.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExcelDriver {
  protected Workbook workBook;

  public void buildExcel(Workbook workBook, File file) throws IOException, FileNotFoundException {
    this.workBook = workBook;
    processExcelSheet();
    try (FileOutputStream out = new FileOutputStream(file)) {
    workBook.write(out);
    }
  }
  
  public void buildExcel(OutputStream outStream, Workbook workBook) throws IOException, FileNotFoundException {
    this.workBook = workBook;
    processExcelSheet();
    workBook.write(outStream);
    outStream.close();
  }

  protected abstract void processExcelSheet();
}
