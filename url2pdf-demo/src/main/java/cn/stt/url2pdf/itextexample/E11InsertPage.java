package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 插入Page
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E11InsertPage {
    public static void main(String[] args) throws Exception {
        FileOutputStream out = new FileOutputStream(FILE_DIR + "E11InsertPage.pdf");
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("1 page"));
        document.newPage();
        document.add(new Paragraph("2 page"));
        document.newPage();
        document.add(new Paragraph("3 page"));
        document.close();
        PdfReader reader = new PdfReader(FILE_DIR + "E11InsertPage.pdf");
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "E11InsertPage2.pdf"));
        stamp.insertPage(2, reader.getPageSize(1));
        ColumnText ct = new ColumnText(null);
        ct.addElement(new Paragraph(24, new Chunk("INSERT PAGE")));
        ct.setCanvas(stamp.getOverContent(2));
        ct.setSimpleColumn(36, 36, 559, 770);
        stamp.close();
        reader.close();
    }
}
