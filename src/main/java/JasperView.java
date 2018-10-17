import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;

public class JasperView {

    public static void main(String[] args) throws IOException, JRException {
        Parser parser = new Parser();
        Paper paper = new Paper();
        parser.parse(paper);

        JasperPrint jasperPrint = new JasperPrint();
        JRPrintPage page = new JRBasePrintPage();
        jasperPrint.addPage(page);
        jasperPrint.setPageWidth(595);
        jasperPrint.setPageHeight(1000);

        Fill.fill_title(paper.getTitle(), jasperPrint, page);
        Fill.fill_header(paper.getHeader(), jasperPrint, page);
        Fill.fill_body(paper.getBody(), jasperPrint, page);
        Fill.fill_footer(paper.getFooter(), jasperPrint, page);

        JRSaver.saveObject(jasperPrint, "PrintServiceReport.jrprint");
        JasperExportManager.exportReportToPdf(jasperPrint);
        JasperViewer jv=new JasperViewer(jasperPrint,false);
        jv.setTitle("Challan");
        jv.setVisible(true);
    }
}
