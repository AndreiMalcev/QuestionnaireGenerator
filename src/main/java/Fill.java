import com.lowagie.text.pdf.Barcode128;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBasePrintRectangle;
import net.sf.jasperreports.engine.base.JRBasePrintText;

public class Fill {

    private static void put_text(JasperPrint jasperPrint, JRPrintPage page, int setX, int setY,
                                 int setWidth, int setHeight, String text) {
        JRPrintText jrprintText = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        jrprintText.setX(setX);
        jrprintText.setY(setY);
        jrprintText.setWidth(setWidth);
        jrprintText.setHeight(setHeight);
        jrprintText.setTextHeight(jrprintText.getHeight());
        jrprintText.setText(text);
        page.addElement(jrprintText);
    }

    private static void put_rectanagle(JasperPrint jasperPrint, JRPrintPage page, int setX, int setY,
                                 int setWidth, int setHeight) {
        JRBasePrintRectangle rectangle = new JRBasePrintRectangle(jasperPrint.getDefaultStyleProvider());
        rectangle.setX(setX);
        rectangle.setY(setY);
        rectangle.setWidth(setWidth);
        rectangle.setHeight(setHeight);
        page.addElement(rectangle);
    }


    private static void multi_ans(){

    }


    public static void fill_title(Title title, JasperPrint jasperPrint, JRPrintPage page) {
        JRPrintText text = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        put_text(jasperPrint, page, 180, 55, 345, 30, title.getText());
    }

    public static void fill_barcode(BarCode barCode, JasperPrint jasperPrint, JRPrintPage page) throws JRException {
        Barcode128 barcode = new Barcode128();
        barcode.setCode("");
        /*
// Set EAN128 data text to encode

        image.setX(45);
        image.setY(55);
        image.setWidth(165);
        image.setHeight(40);
        image.setScaleImage(ScaleImageEnum.CLIP);
        page.addElement(image);
        */
    }

    public static void fill_header(Header header, JasperPrint jasperPrint, JRPrintPage page) {
        put_text(jasperPrint, page, 18, 100, 580, 840, header.getText());
    }

    public static void fill_body(Body body, JasperPrint jasperPrint, JRPrintPage page) {
        int i = 0;
        for (Questions question : body.getQuestions()) {
            if (question.getMulti_marks().length != 0) {
                put_text(jasperPrint, page, 130, 220 + i, 580, 840, question.getMulti_question_header());
                put_text(jasperPrint, page, 450, 220 + i, 580, 840, question.getMulti_marks_header());
            }
            put_text(jasperPrint, page, 14, 220 + i, 580, 840, question.getTitle());
            for (Answers answer : question.getAnswers()) {
                i += 20;
                if (!answer.getMark_name().equals("")) {
                    put_rectanagle(jasperPrint, page, 14, 220 + i, 15, 15);
                }

                put_text(jasperPrint, page, 18, 220 + i, 580, 840, answer.getMark_name());
                put_text(jasperPrint, page, 38, 220 + i, 300, 840, answer.getText());
                if (question.getMulti_marks().length != 0) {
                    for (int j = 0; j < question.getMulti_marks().length; j++) {
                        put_rectanagle(jasperPrint, page, 400 + 30 * j, 220 + i, 15, 15);
                        put_text(jasperPrint, page, 405 + 30 * j, 220 + i, 300, 840, question
                        .getMulti_marks()[j]);
                    }
                }
                i += 20;
            }

        }
    }

    public static void fill_footer(Footer footer, JasperPrint jasperPrint, JRPrintPage page) {
        put_text(jasperPrint, page, 210, 950, 580, 840, footer.getText());
    }
}
