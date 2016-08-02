package com.xyz.crudservice.util;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

public class PDFDocWrapper {
  private Document doc;
  public PDFDocWrapper(Document doc) {
    this.doc = doc;
  }
  
  public void addTable(Element tableElement) throws DocumentException {
    Elements cols = tableElement.select("th");
    Elements rows = tableElement.select("tr");
    
    PdfPTable pdfTable = new PdfPTable(cols.size());
    
    for (int k = 0; k < cols.size(); k++) {
      PdfPCell pdfcell = new PdfPCell(new Paragraph(cols.get(k).text()));
      pdfTable.addCell(pdfcell);
    }
    
    for (int i = 0; i < rows.size(); i++) {
      Element row = rows.get(i);
      Elements cells = row.select("td");

      for (int j = 0; j < cells.size(); j++) {
        PdfPCell pdfcell = new PdfPCell(new Paragraph(cells.get(j).text()));
        pdfTable.addCell(pdfcell);
      }
    }
    doc.add(pdfTable);
  }
}
