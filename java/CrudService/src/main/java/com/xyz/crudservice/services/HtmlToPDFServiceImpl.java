package com.xyz.crudservice.services;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.xyz.crudservice.exceptions.CrudServiceException;
import com.xyz.crudservice.util.PDFDocWrapper;
import com.xyz.crudserviceclient.enums.FailureCause;

@Service
public class HtmlToPDFServiceImpl {
  public byte[] convertHtmlToPDF(Document document) throws FileNotFoundException {
    com.itextpdf.text.Document doc = new com.itextpdf.text.Document();
    ByteArrayOutputStream bstream = new ByteArrayOutputStream();
    try {
      PdfWriter.getInstance(doc, bstream);
      doc.open();
      
      PDFDocWrapper pdfWrap = new PDFDocWrapper(doc);
      
      Element table = document.select("#tab1").get(0);
      pdfWrap.addTable(table);
      
      doc.close();
    } catch (DocumentException e1) {
      throw new CrudServiceException(FailureCause.UNEXPECTED, "Document Exception occured", e1);
    }
    return bstream.toByteArray();
  }
  
  public static void main(String... args) {
    String html = "<html><head></head><body><table id='tab1'><th>Test</th><th>Test1</th><tr><td>Hello</td><td>Hai</td></tr><tr><td>Hello</td><td>Hai</td></tr></table></body></html>";
    Document doc = Jsoup.parse(html);
    try {
      new HtmlToPDFServiceImpl().convertHtmlToPDF(doc);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }
}
