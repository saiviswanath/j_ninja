package com.xyz.crudservice.services;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.xyz.crudservice.exceptions.CrudServiceException;
import com.xyz.crudserviceclient.enums.FailureCause;

@Service
public class HtmlToPDFServiceImpl {
  public byte[] convertHtmlToPDF(Document document) throws FileNotFoundException {
    Element table = document.select("table").get(0);
    Elements cols = table.select("th");
    Elements rows = table.select("tr");

    com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
    ByteArrayOutputStream bstream = new ByteArrayOutputStream();
    
    try {
      PdfWriter.getInstance(doc, bstream);
      doc.open();
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
      doc.close();
    } catch (DocumentException e1) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Document Exception occured", e1);
    }
    return bstream.toByteArray();
  }
  
  public static void main(String... args) {
    String html = "<html><head></head><body><table><th>Test</th><th>Test1</th><tr><td>Hello</td><td>Hai</td></tr><tr><td>Hello</td><td>Hai</td></tr></table></body></html>";
    Document doc = Jsoup.parse(html);
    try {
      new HtmlToPDFServiceImpl().convertHtmlToPDF(doc);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
