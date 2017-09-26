package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 页面大小,页面背景色,页边空白,Title,Author,Subject,Keywords
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E2SetPdfPageArgs {
    public static void main(String[] args) throws Exception {
        //页面大小
        Rectangle rectangle = new Rectangle(PageSize.B5.rotate());
        //页面背景色
        rectangle.setBackgroundColor(BaseColor.ORANGE);
        Document document = new Document(rectangle);
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E2SetPdfPageArgs.pdf"));
        //PDF版本(默认1.4)
        pdfWriter.setPdfVersion(PdfWriter.VERSION_1_2);
        //文档属性
        document.addTitle("Title@sample");
        document.addAuthor("Author@shitongtong");
        document.addSubject("Subject@iText sample");
        document.addKeywords("Keywords@iText");
        document.addCreator("Creator@iText");
        //页边空白
        document.setMargins(10, 20, 30, 40);
        document.open();
        document.add(new Paragraph("Hello World"));
        document.close();
    }
}
