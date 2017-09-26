package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 画图
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E8Draw {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream(FILE_DIR + "E8Draw.pdf"));
        document.open();

        //左右箭头
        document.add(new VerticalPositionMark() {
            public void draw(PdfContentByte canvas, float llx, float lly,
                             float urx, float ury, float y) {
                canvas.beginText();
                BaseFont bf = null;
                try {
                    bf = BaseFont.createFont(BaseFont.ZAPFDINGBATS, "", BaseFont.EMBEDDED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                canvas.setFontAndSize(bf, 12);
                // LEFT
                canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), llx - 10, y, 0);
                // RIGHT
                canvas.showTextAligned(Element.ALIGN_CENTER, String.valueOf((char) 220), urx + 10, y + 8, 180);
                canvas.endText();
            }
        });
        //直线
        Paragraph p1 = new Paragraph("LEFT");
        p1.add(new Chunk(new LineSeparator()));
        p1.add("R");
        document.add(p1);
        //点线
        Paragraph p2 = new Paragraph("LEFT");
        p2.add(new Chunk(new DottedLineSeparator()));
        p2.add("R");
        document.add(p2);
        //下滑线
        LineSeparator UNDERLINE = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -2);
        Paragraph p3 = new Paragraph("NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN");
        p3.add(UNDERLINE);
        document.add(p3);

        document.close();
    }
}
