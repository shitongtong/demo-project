package cn.stt.url2pdf.demo1;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * 测试xml worker 页面包含中文字符的转换
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/19.
 */
public class Demo1HTMLCn {
    public static void main(String[] args) throws Exception {
//        String pdfFile = "D:\\开源世界旅行手册\\开源世界旅行手册.pdf";
//        String htmlFile = "D:\\开源世界旅行手册\\开源世界旅行手册.html";

//        String pdfFile = "D:\\开源世界旅行手册\\demo-htmlcn.pdf";
//        String htmlFile = "D:\\开源世界旅行手册\\demo-cn.html";

        String pdfFile = "d:/test/itext/demo-htmlcn.pdf";
        String htmlFile = "d:/test/itext/demo-cn.html";

        Document document = new Document();
        PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
        pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
        document.open();

        // html文件
        InputStreamReader isr = new InputStreamReader(new FileInputStream(htmlFile), "UTF-8");
        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, isr);
        document.close();
    }
}
