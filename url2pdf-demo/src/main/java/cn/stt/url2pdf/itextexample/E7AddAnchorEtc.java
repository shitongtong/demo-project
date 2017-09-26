package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 插入Anchor, Image, Chapter, Section
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E7AddAnchorEtc {
    public static void main(String[] args) throws Exception{
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream(FILE_DIR+"E7AddAnchorEtc.pdf"));
        document.open();

        //Anchor对象: internal and external links
        Paragraph country = new Paragraph();
        Anchor dest = new Anchor("china", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
        dest.setName("CN");
        dest.setReference("http://www.china.com");//external
        country.add(dest);
        country.add(String.format(": %d sites", 10000));
        document.add(country);
        document.newPage();
        Anchor toUS = new Anchor("Go to first page.", new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD, BaseColor.BLUE));
        toUS.setReference("#CN");//internal
        document.add(toUS);
        //Image对象
        document.newPage();
        Image img = Image.getInstance("D:\\test\\itext\\example\\E5爱符号_84.png");
        img.setAlignment(Image.LEFT | Image.TEXTWRAP);
        img.setBorder(Image.BOX);
        img.setBorderWidth(10);
        img.setBorderColor(BaseColor.WHITE);
        img.scaleToFit(1000, 72);//大小
        img.setRotationDegrees(-30);//旋转
        document.add(img);
        //Chapter, Section对象（目录）
        document.newPage();
        Paragraph title = new Paragraph("Title");
        Chapter chapter = new Chapter(title, 1);
        title = new Paragraph("Section A");
        Section section = chapter.addSection(title);
        section.setBookmarkTitle("bmk");
        section.setIndentation(30);
        section.setBookmarkOpen(false);
        section.setNumberStyle(
                Section.NUMBERSTYLE_DOTTED_WITHOUT_FINAL_DOT);
        Section subsection = section.addSection(new Paragraph("Sub Section A"));
        subsection.setIndentationLeft(20);
        subsection.setNumberDepth(1);
        document.add(chapter);

        document.close();
    }
}
