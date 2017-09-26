package cn.stt.url2pdf.demo3;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/23.
 */
public class Demo3Test {
    public static void main(String[] args) throws Exception{
        String pdfFile = "D:\\开源世界旅行手册\\test\\服务管理.pdf";
        Document document = new Document();
        PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
        document.open();

        String htmlFile = "D:\\开源世界旅行手册\\test\\服务管理.html";
        try {

            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, new FileReader(htmlFile));
        }catch (Exception e){
            e.printStackTrace();
            document.add(new Paragraph("Hello World"));
        }
        document.close();
    }
}
