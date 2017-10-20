package cn.stt.url2pdf.merge;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/10/9.
 */
public class MergeUtil {

    /**
     * @param mergeFileList 待合并的pdf文件列表
     * @param outFile       合并完的pdf文件
     */
    public static void merge(List<File> mergeFileList, File outFile) throws Exception {
        List<PdfReader> readers = new ArrayList<>();
        PdfReader reader;
        for (File file : mergeFileList) {
            reader = new PdfReader(file.getAbsolutePath());
            readers.add(reader);
        }
        FileOutputStream out = new FileOutputStream(outFile);
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        int pageOfCurrentReaderPDF = 0;
        Iterator<PdfReader> iteratorPDFReader = readers.iterator();
        while (iteratorPDFReader.hasNext()) {
            PdfReader pdfReader = iteratorPDFReader.next();
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
