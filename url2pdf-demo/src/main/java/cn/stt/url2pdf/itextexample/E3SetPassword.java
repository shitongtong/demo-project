package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 设置密码
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E3SetPassword {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E3SetPassword.pdf"));
        // 设置密码为："World"
        //报异常：java.lang.NoClassDefFoundError: org/bouncycastle/asn1/ASN1Encodable
        //需引入bcprov-jdk15on jar
        pdfWriter.setEncryption("Hello".getBytes(), "World".getBytes(), PdfWriter.ALLOW_SCREENREADERS, PdfWriter.STANDARD_ENCRYPTION_128);
        document.open();
        document.add(new Paragraph("Hello World"));
        document.close();
    }
}
