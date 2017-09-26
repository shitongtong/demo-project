package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 生成一个pdf
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E1CreatePDF {
    public static void main(String[] args) throws Exception {
        //Step 1—Create a Document.
        Document document = new Document();
        //Step 2—Get a PdfWriter instance.
        PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E1CreatePDF.pdf"));
        //Step 3—Open the Document.
        document.open();
        //Step 4—Add content.
        document.add(new Paragraph("Hello World"));
        //Step 5—Close the Document.
        document.close();
    }
}
