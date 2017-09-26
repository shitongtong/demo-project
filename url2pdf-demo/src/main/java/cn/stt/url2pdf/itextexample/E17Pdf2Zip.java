package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 压缩PDF到Zip
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E17Pdf2Zip {
    public static void main(String[] args) throws Exception {
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(FILE_DIR + "E17Pdf2Zip.zip"));
        for (int i = 1; i <= 3; i++) {
            ZipEntry entry = new ZipEntry("E17Pdf2Zip_" + i + ".pdf");
            zip.putNextEntry(entry);
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, zip);
            writer.setCloseStream(false);
            document.open();
            document.add(new Paragraph("Hello " + i));
            document.close();
        }
        zip.closeEntry();
    }

    @Test
    public void pdfZip() throws Exception {
        ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(FILE_DIR + "E17Pdf2ZipTest.zip"));
        File dir = new File(FILE_DIR);
        String[] fileNames = dir.list();
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        byte[] bufs = new byte[1024 * 10];
        for (int i = 0; i < fileNames.length; i++) {
            String fileName = fileNames[i];
            if (!fileName.contains(".pdf")) {
                continue;
            }
            ZipEntry entry = new ZipEntry(fileName);
            zip.putNextEntry(entry);
            //读取待压缩的文件并写进压缩包里
            fis = new FileInputStream(FILE_DIR + fileName);
            bis = new BufferedInputStream(fis, 1024 * 10);
            int read = 0;
            while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                zip.write(bufs, 0, read);
            }
        }
        zip.close();
        bis.close();
        fis.close();
    }
}
