package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Version;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 添加Page
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E4AddPage {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E4AddPage.pdf"));
        document.open();
        document.add(new Paragraph("First page"));
        document.add(new Paragraph(Version.getInstance().getVersion()));
        document.newPage();
        pdfWriter.setPageEmpty(false);
        document.newPage();
        document.add(new Paragraph("New page"));
        document.close();
    }
}
