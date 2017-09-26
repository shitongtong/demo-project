package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 设置页眉页脚Header, Footer
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E14SetHeaderAndFooter {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E14SetHeaderAndFooter.pdf"));
        pdfWriter.setPageEvent(new PdfPageEventHelper() {
            public void onEndPage(PdfWriter writer, Document document) {
                PdfContentByte cb = writer.getDirectContent();
                cb.saveState();
                cb.beginText();
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                cb.setFontAndSize(bf, 10);
                //Header
                float x = document.top(-20);
                //左
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "H-Left", document.left(), x, 0);
                //中
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
                        writer.getPageNumber() + " page",
                        (document.right() + document.left()) / 2,
                        x, 0);
                //右
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "H-Right", document.right(), x, 0);
                //Footer
                float y = document.bottom(-20);
                //左
                cb.showTextAligned(PdfContentByte.ALIGN_LEFT, "F-Left", document.left(), y, 0);
                //中
                cb.showTextAligned(PdfContentByte.ALIGN_CENTER,
                        writer.getPageNumber() + " page",
                        (document.right() + document.left()) / 2,
                        y, 0);
                //右
                cb.showTextAligned(PdfContentByte.ALIGN_RIGHT, "F-Right", document.right(), y, 0);
                cb.endText();
                cb.restoreState();
            }
        });
        document.open();
        document.add(new Paragraph("1 page"));
        document.newPage();
        document.add(new Paragraph("2 page"));
        document.newPage();
        document.add(new Paragraph("3 page"));
        document.newPage();
        document.add(new Paragraph("4 page"));
        document.close();
    }
}
