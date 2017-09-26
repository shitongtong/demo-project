package cn.stt.itext7;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.licensekey.LicenseKey;

import java.io.File;
import java.io.IOException;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/21.
 */
public class Demo1 {
    /** The Base URI of the HTML page. */
    public static final String BASEURI = "iText7-demo/src/main/resources/html/";
    /** The path to the source HTML file. */
    public static final String SRC = String.format("%s1_no_css.html", BASEURI);
    /** The target folder for the result. */
    public static final String TARGET = "target/results/ch02/";
    /** The path to the resulting PDF file. */
    public static final String DEST = String.format("%smovie01.pdf", TARGET);

    public static void main(String[] args) throws IOException {
        LicenseKey.loadLicenseFile(System.getenv("ITEXT7_LICENSEKEY") + "/itextkey-html2pdf_typography.xml");
        File file = new File(TARGET);
        file.mkdirs();
        Demo1 app = new Demo1();
        app.createPdf(SRC, DEST);
    }

    public void createPdf(String src, String dest) throws IOException {
        HtmlConverter.convertToPdf(new File(src), new File(dest));
    }
}
