import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.*;
import net.sf.jasperreports.engine.design.JRDesignStyle;
import net.sf.jasperreports.engine.util.JRSaver;
import net.sf.jasperreports.view.JasperViewer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

    public void fill() throws JRException
    {
        long start = System.currentTimeMillis();
        JasperPrint jasperPrint = getJasperPrint();
        JRSaver.saveObject(jasperPrint, "PrintServiceReport.jrprint");
        JasperExportManager.exportReportToPdf(jasperPrint);
        JasperViewer jv=new JasperViewer(jasperPrint,false);
        jv.setTitle("Challan");
        jv.setVisible(true);
        System.err.println("Filling time : " + (System.currentTimeMillis() - start));
    }

    private static JasperPrint getJasperPrint() throws JRException
    {
        //JasperPrint
        JasperPrint jasperPrint = new JasperPrint();
        jasperPrint.setName("NoReport");
        jasperPrint.setPageWidth(595);
        jasperPrint.setPageHeight(842);
        //Fonts
        JRDesignStyle normalStyle = new JRDesignStyle();
        normalStyle.setName("Sans_Normal");
        normalStyle.setDefault(true);
        normalStyle.setPdfFontName("Helvetica");
        normalStyle.setPdfEncoding("Cp1252");
        normalStyle.setPdfEmbedded(false);
        jasperPrint.addStyle(normalStyle);

        JRDesignStyle boldStyle = new JRDesignStyle();
        boldStyle.setName("Sans_Bold");
        boldStyle.setFontSize(8);
        boldStyle.setBold(true);
        boldStyle.setPdfEncoding("Cp1252");
        boldStyle.setPdfEmbedded(false);
        jasperPrint.addStyle(boldStyle);

        JRDesignStyle italicStyle = new JRDesignStyle();
        italicStyle.setName("Sans_Italic");
        italicStyle.setItalic(true);
        italicStyle.setPdfEncoding("Cp1252");
        italicStyle.setPdfEmbedded(false);
        jasperPrint.addStyle(italicStyle);


        JRPrintPage page = new JRBasePrintPage();
        JRPrintLine line = new JRBasePrintLine(jasperPrint.getDefaultStyleProvider());
        line.setX(40);
        line.setY(50);
        line.setWidth(515);
        line.setHeight(0);
        page.addElement(line);
        JRPrintText text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        text.setX(210);
        text.setY(55);
        text.setWidth(345);
        text.setHeight(30);
        text.setTextHeight(text.getHeight());
        text.setLineSpacingFactor(1.3133681f);
        text.setLeadingOffset(-4.955078f);
        text.setStyle(boldStyle);
        text.setText("JasperReports Project Description");
        page.addElement(text);
        text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        text.setX(210);
        text.setY(85);
        text.setWidth(325);
        text.setHeight(15);
        text.setTextHeight(text.getHeight());
        text.setLineSpacingFactor(1.329241f);
        text.setLeadingOffset(-4.076172f);
        text.setStyle(italicStyle);
        text.setText((new SimpleDateFormat("EEE, MMM d, yyyy")).format(new Date()));
        page.addElement(text);
        text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        text.setX(40);
        text.setY(150);
        text.setWidth(515);
        text.setHeight(200);
        text.setTextHeight(text.getHeight());
        text.setLineSpacingFactor(1.329241f);
        text.setLeadingOffset(-4.076172f);
        text.setStyle(normalStyle);
        text.setText(
                "JasperReports is a powerful report-generating tool that has the ability to deliver "+
                        "rich content onto the screen, to the printer or into PDF, HTML, XLS, CSV or XML files.\n\n" +
                        "It is entirely written in Java and can be used in a variety of Java enabled applications, " +
                        "including J2EE or Web applications, to generate dynamic content.\n\n" +
                        "Its main purpose is to help creating page oriented, ready to print documents in a simple and flexible manner."
        );
        page.addElement(text);

        jasperPrint.addPage(page);

        return jasperPrint;
    }

    public static void main(String[] args) throws JRException {
        Main main = new Main();
        main.fill();
    }


}
