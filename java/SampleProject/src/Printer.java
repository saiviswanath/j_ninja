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
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.xyz.db.DBConnector;

public class Printer {

  public static void main(String[] args) {
    List<PrinterJob> jobs = getPrintJobs();
    Map<PrinterJob, Printable> map = new HashMap<>();
    for (PrinterJob job : jobs) {
      map.put(job, new Printer().new SamplePrint());
    }
    showUI(map);
  }


  /**
   * TODO: Define proper schema to iterate over the print records and to populate fields.
   * <record> <recordId>1</recordId> <x-cord>10</x-cord> <y-cord>20</y-cord> </field>
   * <recordId<<x-cord, y-cord>>
   *
   * @return
   */
  private static Map<Integer, Map<String, String>> readCoordinateXML() {
    Map<Integer, Map<String, String>> nestMap = new HashMap<Integer, Map<String, String>>();
    return nestMap;
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

      graphics.drawString("EmpId | ", 40, 10);
      graphics.drawString("EmpName | ", 80, 10);
      graphics.drawString("EmpAddr | ", 120, 10);
      graphics.drawString(getxField() + " | ", 40, 30);
      graphics.drawString(getyField() + " | ", 80, 30);
      graphics.drawString(getzField() + " | ", 120, 30);

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
