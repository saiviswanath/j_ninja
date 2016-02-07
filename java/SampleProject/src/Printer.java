import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Printer {
  private static String xmlCoordConfigFile;

  public static void main(String[] args) {
    xmlCoordConfigFile = args[0];
    List<PrinterJob> jobs = getPrintJobs();
    Map<PrinterJob, Printable> map = new HashMap<>();
    for (PrinterJob job : jobs) {
      map.put(job, new Printer().new SamplePrint());
    }
    showUI(map);
  }

  static class FieldCoord {
    private int xcord;
    private int ycord;

    public FieldCoord(int xcord, int ycord) {
      super();
      this.xcord = xcord;
      this.ycord = ycord;
    }

    /**
     * @return the xcord
     */
    public int getXcord() {
      return xcord;
    }

    /**
     * @param xcord the xcord to set
     */
    public void setXcord(int xcord) {
      this.xcord = xcord;
    }

    /**
     * @return the ycord
     */
    public int getYcord() {
      return ycord;
    }

    /**
     * @param ycord the ycord to set
     */
    public void setYcord(int ycord) {
      this.ycord = ycord;
    }
  }


  /**
   * TODO: Define proper schema to iterate over the print records and to populate fields. <records>
   * <record> <recordId>1</recordId><field1><x-cord></x-cord><y-cord></y-cord></field1>
   * <field2><x-cord></x-cord><y-cord></y-cord></field2>
   * <field3><x-cord></x-cord><y-cord></y-cord></field3><record></records>
   *
   * @return
   */
  private static Map<Integer, Set<FieldCoord>> readCoordinateXML() {
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db;
    Document doc = null;
    try {
      db = dbf.newDocumentBuilder();
      doc = db.parse(new File(xmlCoordConfigFile));
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    Node recordsNode = doc.getFirstChild();
    NodeList recordNodes = recordsNode.getChildNodes();

    Map<Integer, Set<FieldCoord>> map = new HashMap<>();
    Set<FieldCoord> coordset = new LinkedHashSet<>();
    int recIdVal = 0;

    for (int i = 0; i < recordNodes.getLength(); i++) {
      NodeList recordChildren = recordNodes.item(i).getChildNodes();
      for (int j = 0; j < recordChildren.getLength(); j++) {
        Node recNode = recordChildren.item(j);
        if (recNode.getNodeName().equalsIgnoreCase("recordid")) {
          recIdVal = Integer.parseInt(recNode.getTextContent());
          map.put(recIdVal, null);
        } else {
          NodeList coordsList = recNode.getChildNodes();
          FieldCoord fcord =
              new FieldCoord(Integer.parseInt(coordsList.item(0).getTextContent()),
                  Integer.parseInt(coordsList.item(1).getTextContent()));
          coordset.add(fcord);
        }

        if (j == recordChildren.getLength() - 1 && recIdVal != 0) {
          map.put(recIdVal, coordset);
          coordset = new LinkedHashSet<>();
        }
      }
    }
    System.out.println(map);
    return map;
  }

  class SamplePrint implements Printable {
    private String xField = "101";
    private String yField = "Me";
    private String zField = "Addr";

    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
        throws PrinterException {

      if (pageIndex > 0) {
        return NO_SUCH_PAGE;
      }

      Graphics2D g2d = (Graphics2D) graphics;
      g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

      Map<Integer, Set<FieldCoord>> map = readCoordinateXML();
      boolean headerRecord = true;
      for (Map.Entry<Integer, Set<FieldCoord>> entry : map.entrySet()) {
        Set<FieldCoord> filedCoordsSet = entry.getValue();
        Iterator<FieldCoord> it = filedCoordsSet.iterator();
        if (headerRecord) {
          FieldCoord f1 = it.next();
          graphics.drawString("EmpId | ", f1.getXcord(), f1.getYcord());
          FieldCoord f2 = it.next();
          graphics.drawString("EmpName | ", f2.getXcord(), f2.getYcord());
          FieldCoord f3 = it.next();
          graphics.drawString("EmpAddr | ", f3.getXcord(), f3.getYcord());
          headerRecord = false;
        } else {
          FieldCoord f1 = it.next();
          graphics.drawString(getxField() + " | ", f1.getXcord(), f1.getYcord());
          FieldCoord f2 = it.next();
          graphics.drawString(getyField() + " | ", f2.getXcord(), f2.getYcord());
          FieldCoord f3 = it.next();
          graphics.drawString(getzField() + " | ", f3.getXcord(), f3.getYcord());
        }
      }

      return PAGE_EXISTS;
    }

    /**
     * @return the xField
     */
    public String getxField() {
      return xField;
    }

    /**
     * @param xField the xField to set
     */
    public void setxField(String xField) {
      this.xField = xField;
    }

    /**
     * @return the yField
     */
    public String getyField() {
      return yField;
    }

    /**
     * @param yField the yField to set
     */
    public void setyField(String yField) {
      this.yField = yField;
    }

    /**
     * @return the zField
     */
    public String getzField() {
      return zField;
    }

    /**
     * @param zField the zField to set
     */
    public void setzField(String zField) {
      this.zField = zField;
    }


  }

  private static List<PrinterJob> getPrintJobs() {
    List<PrinterJob> listOfJobs = new ArrayList<PrinterJob>();
    // Connection con = DBConnector.getDBConnection();
    // ????
    listOfJobs.add(PrinterJob.getPrinterJob()); // Sample
    return listOfJobs;
  }

  private static void showUI(Map<PrinterJob, Printable> jobMap) {
    JFrame frame = new JFrame();
    JPanel panel = new JPanel();
    panel.setBackground(Color.darkGray);
    int count = 0;
    for (PrinterJob job : jobMap.keySet()) {
      count++;
      JButton button = new JButton("Job " + count);
      button.addActionListener(new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
          if (e.getSource() == button) {
            if (jobMap.get(job) instanceof SamplePrint) {
              printJob(job, new Printer().new SamplePrint());
            }
          }
        }
      });
      panel.add(button);
      frame.getContentPane().add(BorderLayout.CENTER, panel);
      frame.setSize(250, 200);
      frame.setVisible(true);
    }
  }

  public static void printJob(PrinterJob job, Object o) {
    if (o instanceof SamplePrint) {
      job.setPrintable((SamplePrint) o);
    }

    boolean doPrint = job.printDialog();
    if (doPrint) {
      try {
        job.print();
      } catch (PrinterException e) {
        System.out.println("The job did not successfully complete");
      }
    }
  }
}
