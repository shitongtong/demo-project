package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 分割PDF
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/21.
 */
public class E18SplitPDF {
    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "E18SplitPDF.pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("1 page"));
        document.newPage();
        document.add(new Paragraph("2 page"));
        document.newPage();
        document.add(new Paragraph("3 page"));
        document.newPage();
        document.add(new Paragraph("4 page"));
        document.close();
        PdfReader reader = new PdfReader(FILE_DIR + "E18SplitPDF.pdf");
        Document dd = new Document();
        PdfWriter writer = PdfWriter.getInstance(dd, new FileOutputStream(FILE_DIR + "E18SplitPDF1.pdf"));
        dd.open();
        PdfContentByte cb = writer.getDirectContent();
        dd.newPage();
        cb.addTemplate(writer.getImportedPage(reader, 1), 0, 10);
        dd.newPage();
        cb.addTemplate(writer.getImportedPage(reader, 2), 10, 0);
        dd.close();
        writer.close();
        Document dd2 = new Document();
        PdfWriter writer2 = PdfWriter.getInstance(dd2, new FileOutputStream(FILE_DIR + "E18SplitPDF2.pdf"));
        dd2.open();
        PdfContentByte cb2 = writer2.getDirectContent();
        dd2.newPage();
        cb2.addTemplate(writer2.getImportedPage(reader, 3), 0, 0);
        dd2.newPage();
        cb2.addTemplate(writer2.getImportedPage(reader, 4), 0, 0);
        dd2.close();
        writer2.close();
    }
}
