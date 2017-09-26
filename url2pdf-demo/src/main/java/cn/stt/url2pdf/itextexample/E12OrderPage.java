package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 排序page
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E12OrderPage {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E12OrderPage.pdf"));
        writer.setLinearPageMode();
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
        int[] order = {4, 3, 2, 1};
        writer.reorderPages(order);
        document.close();
    }
}
