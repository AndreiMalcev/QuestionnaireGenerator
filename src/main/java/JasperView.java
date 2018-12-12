import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBasePrintPage;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

import java.awt.print.PageFormat;
import java.io.IOException;

public class JasperView {

    public static void main(String[] args) throws IOException, JRException {
     Main main = new Main("C:\\Users\\user\\Desktop\\CSC\\practice\\" +
             "JsonParser\\src\\main\\resources\\anketa_english.json");
     main.creationForms();
    }
}
