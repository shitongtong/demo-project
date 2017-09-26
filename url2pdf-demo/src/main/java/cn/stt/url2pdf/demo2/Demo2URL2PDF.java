package cn.stt.url2pdf.demo2;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.RectangleReadOnly;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * 无效啊：document.close();： ExceptionConverter: java.io.IOException: The document has no pages.
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class Demo2URL2PDF {
    public static void main(String[] args) throws IOException, DocumentException {
        String htmlPath = "D:\\test\\itext\\demo-cn.html";
        htmlPath = "http://www.micmiu.com/os/linux/shell-dev-null/";
        String pdfPath = "D:\\test\\itext\\demo2-demo-cn.pdf";

        BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        MyFontProvider myFontProvider = new MyFontProvider(BaseColor.BLACK, "", "", false, false, 16, 1, baseFont);
        // step 1
        Document document = new Document(PageSize.A4);
        Rectangle A42 = new RectangleReadOnly(800, 700);
        document.setPageSize(A42);
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document,
                new FileInputStream(htmlPath), null, Charset.forName("UTF-8"), myFontProvider);
        // step 5
        document.close();
    }
}
