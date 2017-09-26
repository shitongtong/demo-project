package cn.stt.url2pdf.itextexample;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.stt.url2pdf.itextexample.Constants.FILE_DIR;

/**
 * 合并PDF
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/21.
 */
public class E19MergePDF {
    public static void main(String[] args) throws Exception {
        PdfReader reader1 = new PdfReader(FILE_DIR + "E18SplitPDF1.pdf");
        PdfReader reader2 = new PdfReader(FILE_DIR + "E18SplitPDF2.pdf");
        FileOutputStream out = new FileOutputStream(FILE_DIR + "E19MergePDF.pdf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
//        int totalPages = 0;
//        totalPages += reader1.getNumberOfPages();
//        totalPages += reader2.getNumberOfPages();
        List<PdfReader> readers = new ArrayList<>();
        readers.add(reader1);
        readers.add(reader2);
        int pageOfCurrentReaderPDF = 0;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();
        // Loop through the PDF files and add to the output.
        while (iteratorPDFReader.hasNext()) {
            PdfReader pdfReader = iteratorPDFReader.next();
            // Create a new page in the target for each source page.
            while (pageOfCurrentReaderPDF < pdfReader.getNumberOfPages()) {
                document.newPage();
                pageOfCurrentReaderPDF++;
                PdfImportedPage page = writer.getImportedPage(pdfReader, pageOfCurrentReaderPDF);
                cb.addTemplate(page, 0, 0);
            }
            pageOfCurrentReaderPDF = 0;
        }
        out.flush();
        document.close();
        out.close();
    }
}
