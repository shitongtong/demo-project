package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 插入Chunk, Phrase, Paragraph, List
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/20.
 */
public class E6AddChunkEtc {
    public static void main(String[] args) throws Exception {
        Document document = new Document();
        PdfWriter.getInstance(document,new FileOutputStream(FILE_DIR+"E6AddChunkEtc.pdf"));
        document.open();

        //Chunk对象: a String, a Font, and some attributes
        document.add(new Chunk("China"));
        document.add(new Chunk(" "));
        Font font = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
        Chunk id = new Chunk("chinese", font);
        id.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
        id.setTextRise(6);
        document.add(id);
        document.add(Chunk.NEWLINE);
        document.add(new Chunk("Japan"));
        document.add(new Chunk(" "));
        Font font2 = new Font(Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.WHITE);
        Chunk id2 = new Chunk("japanese", font2);
        id2.setBackground(BaseColor.BLACK, 1f, 0.5f, 1f, 1.5f);
        id2.setTextRise(6);
        id2.setUnderline(0.2f, -2f);
        document.add(id2);
        document.add(Chunk.NEWLINE);
        //Phrase对象: a List of Chunks with leading
        document.newPage();
        document.add(new Phrase("Phrase page"));
        Phrase director = new Phrase();
        Chunk name = new Chunk("China");
        name.setUnderline(0.2f, -2f);
        director.add(name);
        director.add(new Chunk(","));
        director.add(new Chunk(" "));
        director.add(new Chunk("chinese"));
        director.setLeading(24);
        document.add(director);
        Phrase director2 = new Phrase();
        Chunk name2 = new Chunk("Japan");
        name2.setUnderline(0.2f, -2f);
        director2.add(name2);
        director2.add(new Chunk(","));
        director2.add(new Chunk(" "));
        director2.add(new Chunk("japanese"));
        director2.setLeading(24);
        document.add(director2);
        //Paragraph对象: a Phrase with extra properties and a newline
        document.newPage();
        document.add(new Paragraph("Paragraph page"));
        Paragraph info = new Paragraph();
        info.add(new Chunk("China "));
        info.add(new Chunk("chinese"));
        info.add(Chunk.NEWLINE);
        info.add(new Phrase("Japan "));
        info.add(new Phrase("japanese"));
        document.add(info);
        //List对象: a sequence of Paragraphs called ListItem
        document.newPage();
        List list = new List(List.ORDERED);
        for (int i = 0; i < 10; i++) {
            ListItem item = new ListItem(String.format("%s: %d movies",
                    "country" + (i + 1), (i + 1) * 100), new Font(
                    Font.FontFamily.HELVETICA, 6, Font.BOLD, BaseColor.BLUE));
            List movielist = new List(List.ORDERED, List.ALPHABETICAL);
            movielist.setLowercase(List.LOWERCASE);
            for (int j = 0; j < 5; j++) {
                ListItem movieitem = new ListItem("Title" + (j + 1));
                List directorlist = new List(List.UNORDERED);
                for (int k = 0; k < 3; k++) {
                    directorlist.add(String.format("%s, %s", "Name1" + (k + 1),
                            "Name2" + (k + 1)));
                }
                movieitem.add(directorlist);
                movielist.add(movieitem);
            }
            item.add(movielist);
            list.add(item);
        }
        document.add(list);

        document.close();
    }
}
