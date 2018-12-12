import com.lowagie.text.pdf.codec.Base64;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import java.io.*;
import java.util.ArrayList;

public class Main {
    private String pathToJson;
    private int HEIGHT = 840;
    private int WIDTH = 590;


    public Main(String pathToJson) {
        this.pathToJson = pathToJson;
    }


    public void creationForms() throws IOException, JRException {
        Parser parser = new Parser(pathToJson);
        Paper paper = new Paper();
        parser.parse(paper);
        JasperPrint jasperPrint = new JasperPrint();
        jasperPrint.setPageWidth(WIDTH);
        jasperPrint.setPageHeight(HEIGHT);

        ArrayList<JRPrintElement> elements = new ArrayList<>();
        elements.add(Fill.fillTitle(paper.getTitle(), jasperPrint));
        elements.add(Fill.fillHeader(paper.getHeader(), jasperPrint));
        elements.addAll(Fill.fillBody(paper.getBody(), jasperPrint));
        elements.add(Fill.fillFooter(paper.getFooter(), jasperPrint));
        int length = elements.get(elements.size() - 1).getY() / jasperPrint.getPageHeight() + 1;
        JRPrintPage[] pages = new JRPrintPage[length];
        Aruco aruco = new Aruco(jasperPrint);
        for (int i = 0; i < pages.length; i++) {
             pages[i] = new JRBasePrintPage();
             aruco.putAruco(pages[i]);
             pages[i].addElement(Fill.fillBarcode(paper.getBarCode(), jasperPrint, i));
        }

        for (JRPrintElement element : elements) {
            int i = element.getY() / jasperPrint.getPageHeight();
            if (i > 0) {
                element.setY(element.getY() % jasperPrint.getPageHeight() + 70);
            }
            else {
                element.setY(element.getY() % jasperPrint.getPageHeight());
            }
            pages[i].addElement(element);

        }
        for (JRPrintPage page: pages) {
            jasperPrint.addPage(page);
        }
        JRXMLParser jrxmlParser = new JRXMLParser(JasperExportManager.exportReportToXml(jasperPrint));
        SearchAnswer searchAnswer = new SearchAnswer();
        jrxmlParser.coordinate(searchAnswer);
        JasperViewer jv = new JasperViewer(jasperPrint, false);
        jv.setTitle("Challan");
        jv.setVisible(true);
       // JasperExportManager.exportReportToPdfFile(jasperPrint,"report.pdf");
    }
}