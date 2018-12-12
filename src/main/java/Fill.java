import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.base.JRBasePrintImage;
import net.sf.jasperreports.engine.base.JRBasePrintRectangle;
import net.sf.jasperreports.engine.base.JRBasePrintText;
import net.sf.jasperreports.engine.type.ScaleImageEnum;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;

public class Fill {

    private static JRPrintElement putText(JasperPrint jasperPrint, int setX, int setY,
                                int setWidth, int setHeight, String text) {
        JRPrintText jrprintText = new JRBasePrintText(jasperPrint.getDefaultStyleProvider());
        jrprintText.setX(setX);
        jrprintText.setY(setY);
        jrprintText.setWidth(setWidth);
        jrprintText.setHeight(setHeight);
        jrprintText.setTextHeight(jrprintText.getHeight());
        jrprintText.setText(text);
        return jrprintText;
    }


    private static JRPrintElement putRectanagle(JasperPrint jasperPrint, int setX, int setY,
                                      int setWidth, int setHeight, boolean answer) {
        JRBasePrintRectangle rectangle = new JRBasePrintRectangle(jasperPrint.getDefaultStyleProvider());
        rectangle.setX(setX);
        rectangle.setY(setY);
        rectangle.setWidth(setWidth);
        rectangle.setHeight(setHeight);
        if (answer){
            UUID uuid = new UUID(setX, setY);
            rectangle.setUUID(uuid);
        }
        return rectangle;
    }


    public static JRPrintElement fillTitle(Title title, JasperPrint jasperPrint) {
        return putText(jasperPrint, 180, 55, 345, 200, title.getText());
    }

    public static JRPrintElement fillBarcode(BarCode barCode, JasperPrint jasperPrint, int page_number) {
        Code39Bean bean = new Code39Bean();
        final int dpi = 150;
        bean.setModuleWidth(UnitConv.in2mm(1.0f / dpi));
        bean.setWideFactor(3);
        bean.doQuietZone(false);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try  {
            BitmapCanvasProvider canvas = new BitmapCanvasProvider(
                    out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
            bean.generateBarcode(canvas, barCode.getForm_id() + "$" + barCode.getUser_id() + "$" +
                    page_number);
            canvas.finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
        JRPrintImage image = new JRBasePrintImage(jasperPrint.getDefaultStyleProvider());
        image.setX(250);
        image.setY(10);
        image.setWidth(165);
        image.setHeight(40);
        image.setScaleImage(ScaleImageEnum.CLIP);
        image.setRenderer(
                JRImageRenderer.getInstance(
                        out.toByteArray()
                )
        );
       return image;
    }


    public static JRPrintElement fillHeader(Header header, JasperPrint jasperPrint) {
        return putText(jasperPrint, 50, 100, 500, 200, header.getText());
    }


    public static ArrayList<JRPrintElement> fillBody(Body body, JasperPrint jasperPrint) {
        int i = 0;
        ArrayList<JRPrintElement> list = new ArrayList<>();
        for (Questions question : body.getQuestions()) {
            if (question.getMulti_marks().length != 0) {
                list.add(putText(jasperPrint, 130, 220 + i, 500, 300 + i,
                        question.getMulti_question_header()));
                list.add(putText(jasperPrint, 450, 220 + i, 500, 300 + i,
                        question.getMulti_marks_header()));
            }
            list.add(putText(jasperPrint, 50, 220 + i, 500, 300 + i, question.getTitle()));
            for (Answers answer : question.getAnswers()) {
                i += 20;
                if (!answer.getMark_name().equals("")) {
                    list.add(putRectanagle(jasperPrint, 50, 220 + i, 15, 15, false));
                }

                list.add(putText(jasperPrint, 53, 220 + i, 500, 300 + i, answer.getMark_name()));
                list.add(putText(jasperPrint, 70, 220 + i, 300, 300 + i, answer.getText()));
                if (question.getMulti_marks().length != 0) {
                    for (int j = 0; j < question.getMulti_marks().length; j++) {
                        list.add(putRectanagle(jasperPrint, 400 + 30 * j, 220 + i, 15, 15, true));
                        list.add(putText(jasperPrint, 405 + 30 * j, 220 + i, 300, 300 + i, question
                        .getMulti_marks()[j]));
                    }
                }
                i += 20;
            }
        }
        return list;
    }


    public static JRPrintElement fillFooter(Footer footer, JasperPrint jasperPrint) {
        return putText(jasperPrint, 210, 950, 500, 1200, footer.getText());
    }
}