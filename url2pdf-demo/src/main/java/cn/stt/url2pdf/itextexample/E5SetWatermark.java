package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 添加水印(背景图)
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E5SetWatermark {
    public static void main(String[] args) throws Exception {
//        PdfReader reader = new PdfReader(FILE_DIR + "E5SetWatermark_source.pdf");
        //图片水印
        String imageName = FILE_DIR + "E5小幺鸡.png";
        setWatermark_image(new PdfReader(FILE_DIR + "E5SetWatermark_source.pdf"), imageName);

        //文字水印
        String text = "DUPLICATE";
        setWatermark_text(new PdfReader(FILE_DIR + "E5SetWatermark_source.pdf"), text);

        //背景图
        String backgroundImageName = FILE_DIR + "E5爱符号_84.png";
        setWatermark_background_image(new PdfReader(FILE_DIR + "E5SetWatermark_source.pdf"), backgroundImageName);

//        reader.close();
    }

    /**
     * 图片水印
     *
     * @param reader
     * @param imageName
     */
    private static void setWatermark_image(PdfReader reader, String imageName) throws Exception {
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "E5SetWatermark_image.pdf"));
        Image img = Image.getInstance(imageName);
        img.setAbsolutePosition(200, 400);
        int numberOfPages = reader.getNumberOfPages();
        PdfContentByte under;
        for (int i = 0; i < numberOfPages; i++) {
            under = stamp.getUnderContent(i + 1);
            under.addImage(img);
        }

        stamp.close();
        reader.close();
    }

    /**
     * 文字水印
     *
     * @param reader
     * @param text
     */
    private static void setWatermark_text(PdfReader reader, String text) throws Exception {
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "E5SetWatermark_text.pdf"));
        PdfContentByte over = stamp.getOverContent(reader.getNumberOfPages());
        over.beginText();
        BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.EMBEDDED);
        over.setFontAndSize(bf, 18);
        over.setTextMatrix(30, 30);
        over.showTextAligned(Element.ALIGN_LEFT, text, 230, 430, 45);
        over.endText();
        stamp.close();
        reader.close();
    }

    /**
     * 背景图
     *
     * @param reader
     * @param backgroundImageName
     */
    private static void setWatermark_background_image(PdfReader reader, String backgroundImageName) throws Exception {
        PdfStamper stamp = new PdfStamper(reader, new FileOutputStream(FILE_DIR + "E5SetWatermark_background_image.pdf"));
        Image img2 = Image.getInstance(backgroundImageName);
        img2.setAbsolutePosition(0, 0);
        PdfContentByte under2 = stamp.getUnderContent(reader.getNumberOfPages());
        under2.addImage(img2);
        stamp.close();
        reader.close();
    }
}
