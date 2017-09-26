package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Version;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 删除Page
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E10DeletePage {
    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "E10DeletePage.pdf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("First page"));
        document.add(new Paragraph(Version.getInstance().getVersion()));
        document.newPage();
        writer.setPageEmpty(false);
        document.newPage();
        document.add(new Paragraph("New page"));
        document.close();
        PdfReader reader = new PdfReader(FILE_DIR + "E10DeletePage.pdf");
        reader.selectPages("1,3");
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "E10DeletePage2.pdf"));
        stamp.close();
        reader.close();
    }
}
