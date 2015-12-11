package com.xyz.java;

import java.io.File;
import java.sql.SQLException;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import oracle.jdbc.pool.OracleConnectionPoolDataSource;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xyz.beans.java.EmployeeBean;
import com.xyz.java.dao.EmployeeDAO;
import com.xyz.java.dao.EmployeeDAOImpl;
import com.xyz.java.utils.ExcelUtils;

public class EmployeeExcelDriver extends AbstractExcelDriver {
  private String sheetName;

  public EmployeeExcelDriver(String sheetName) {
    this.sheetName = sheetName;
  }

  @Override
  protected void processExcelSheet() {
    Sheet sheet = ExcelUtils.buildSheet(super.workBook, sheetName);
    sheet.setDisplayGridlines(true);
    EmployeeDAO empDao = new EmployeeDAOImpl();
    try {
      Set<EmployeeBean> employeeSet = empDao.getAllEmployees();
      int rowCount = 0;
      for (EmployeeBean empBean : employeeSet) {
        Row row = sheet.createRow(rowCount++);
        row.setHeightInPoints(30);
        Font font = ExcelUtils.setFont(super.workBook, HSSFColor.BLACK.index, true, 10, "");
        CellStyle cellStyle =
            ExcelUtils.setStyle(super.workBook, font, false, HSSFColor.WHITE.index,
                CellStyle.ALIGN_RIGHT, "", false, false);
        ExcelUtils.addCell(row, cellStyle, (short) 0, Cell.CELL_TYPE_NUMERIC, empBean.getId());
        ExcelUtils.addCell(row, cellStyle, (short) 1, Cell.CELL_TYPE_STRING, empBean.getLastName());
        ExcelUtils
        .addCell(row, cellStyle, (short) 2, Cell.CELL_TYPE_STRING, empBean.getFirstName());
        ExcelUtils.addCell(row, cellStyle, (short) 3, Cell.CELL_TYPE_STRING,
            empBean.getPhoneNumber());
        ExcelUtils.addCell(row, cellStyle, (short) 4, Cell.CELL_TYPE_STRING, empBean.getEmail());

        CellStyle dateCellStyle =
            ExcelUtils.setStyle(super.workBook, font, false, HSSFColor.WHITE.index,
                CellStyle.ALIGN_RIGHT, "m/d/yy", false, false);
        ExcelUtils.addCell(row, dateCellStyle, (short) 5, Cell.CELL_TYPE_BLANK, empBean.getHireDate());
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

  }

  public static void main(String... args) throws Exception {

    System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
        "org.apache.naming.java.javaURLContextFactory");
    System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
    Context context = new InitialContext();

    OracleConnectionPoolDataSource ds = new OracleConnectionPoolDataSource();
    ds.setUser("hr");
    ds.setPassword("hr");
    ds.setURL("jdbc:oracle:thin:@//localhost:1521/XE");
    String dsName = "jdbc/XE";

    context.createSubcontext("java:");
    context.createSubcontext("java:comp");
    context.createSubcontext("java:comp/env");
    context.createSubcontext("java:comp/env/jdbc");

    context.bind("java:comp/env/" + dsName, ds);


    AbstractExcelDriver driver = new EmployeeExcelDriver("EmployeeSheet");
    driver.buildExcel(new XSSFWorkbook(), new File("E:\\Sample.xlsx"));
  }

}
