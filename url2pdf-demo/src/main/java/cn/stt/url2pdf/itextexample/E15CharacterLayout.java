package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 左右文字
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E15CharacterLayout {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E15CharacterLayout.pdf"));
        document.open();
        PdfContentByte canvas = writer.getDirectContent();
        Phrase phrase1 = new Phrase("This is a test!left");
        Phrase phrase2 = new Phrase("This is a test!right");
        Phrase phrase3 = new Phrase("This is a test!center");
        ColumnText.showTextAligned(canvas, Element.ALIGN_LEFT, phrase1, 10, 500, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_RIGHT, phrase2, 10, 536, 0);
        ColumnText.showTextAligned(canvas, Element.ALIGN_CENTER, phrase3, 10, 572, 0);
        document.close();
    }
}
