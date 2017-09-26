package cn.stt.url2pdf.demo3;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * html文件生成pdf
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/21.
 */
public class Demo3Html2PDF {

    public static void main(String[] args) throws Exception {
        String htmlPath = "D:\\开源世界旅行手册\\Grub 配置文件.html";
        String cssPath = "D:\\开源世界旅行手册\\开源世界旅行手册_files\\docbook.css";
        String pdfPath = "D:\\开源世界旅行手册\\Grub 配置文件.pdf";
        html2Pdf(htmlPath, cssPath,pdfPath);
    }

    public static void html2Pdf(String htmlPath, String cssPath,String pdfPath) throws Exception {
        // step 1
        Document document = new Document();

        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
        writer.setInitialLeading(12.5f);

        // step 3
        document.open();

        // step 4

        // CSS
        CSSResolver cssResolver = new StyleAttrCSSResolver();
        CssFile cssFile = XMLWorkerHelper.getCSS(new FileInputStream(cssPath));
        cssResolver.addCss(cssFile);

        // HTML
        HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
        htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

        // Pipelines
        PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
        HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
        CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

        // XML Worker
        XMLWorker worker = new XMLWorker(css, true);
        XMLParser p = new XMLParser(worker);
        p.parse(Html2PdfUtil.tidyHtml(new FileInputStream(htmlPath)));

        // step 5
        document.close();
    }
}
