package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTransition;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 幻灯片放映
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E16SlideShow {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E16SlideShow.pdf"));
        writer.setPdfVersion(PdfWriter.VERSION_1_5);
        writer.setViewerPreferences(PdfWriter.PageModeFullScreen);//全屏
        writer.setPageEvent(new PdfPageEventHelper() {
            public void onStartPage(PdfWriter writer, Document document) {
                writer.setTransition(new PdfTransition(PdfTransition.DISSOLVE, 3));
                writer.setDuration(5);//间隔时间
            }
        });
        document.open();
        document.add(new Paragraph("1 page"));
        document.newPage();
        document.add(new Paragraph("2 page"));
        document.newPage();
        document.add(new Paragraph("3 page"));
        document.newPage();
        document.add(new Paragraph("4 page"));
        document.newPage();
        document.add(new Paragraph("5 page"));
        document.close();
    }
}
