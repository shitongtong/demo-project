package cn.stt.url2pdf.demo3;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * HTML文件转换为PDF
 * 生成开源世界旅行手册.pdf
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/19.
 */
public class Demo3URL2PDF {

    private static List<File> pdfFileList = new ArrayList<>();
    private static Pattern pattern = Pattern.compile("[\\s\\\\/:\\*\\?\\\"<>\\|]");

    @Test
    public void test() {
        String fileName = "第 1 章 GUI? CLI?.pdf";
        fileName = "dsds? ".trim();
        boolean matches1 = fileName.matches("[^\\s\\\\/:\\*\\?\\\"<>\\|](\\x20|[^\\s\\\\/:\\*\\?\\\"<>\\|])*[^\\s\\\\/:\\*\\?\\\"<>\\|\\.]$");
        System.out.println(matches1);
        Matcher matcher = pattern.matcher(fileName);
        boolean matches = matcher.matches();
        System.out.println(matches);
        fileName = matcher.replaceAll("-"); // 将匹配到的非法字符以空替换
        System.out.println(fileName);
    }

    public static void main(String[] args) throws Exception {
        long begin = System.currentTimeMillis();

        String url = "https://i.linuxtoy.org/docs/guide/index.html";
//        url = "https://i.linuxtoy.org/docs/guide/ch22s05.html";
        // 直接把网页内容转为PDF文件
//        String pdfFile = "D:/开源世界旅行手册/开源世界旅行手册ch22s05.pdf";
        String pdfDir = "D:\\开源世界旅行手册\\";
        Demo3URL2PDF.parseURL2PDFFile(pdfDir, url);
        mergePDF(pdfDir);

        System.out.println("总时间：" + (System.currentTimeMillis() - begin) / 1000 + "秒");
    }

    public static void mergePDF(String pdfDir) throws Exception {
        long begin = System.currentTimeMillis();

        FileOutputStream out = new FileOutputStream(pdfDir + "开源世界旅行手册.pdf");
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, out);
        document.open();
        PdfContentByte cb = writer.getDirectContent();
        int size = pdfFileList.size();
        int pageOfCurrentReaderPDF = 0;
        for (int i = 0; i < size; i++) {
            String absolutePath = pdfFileList.get(i).getAbsolutePath();
            PdfReader pdfReader = new PdfReader(absolutePath);
            int numberOfPages = pdfReader.getNumberOfPages();
            for (int j = 0; j < numberOfPages; j++) {
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
        System.out.println("pdf合并时间：" + (System.currentTimeMillis() - begin) / 1000 + "秒");
    }

    /**
     * 直接把网页内容转为PDF文件
     *
     * @param pdfDir 保存的目录
     * @param url
     * @throws Exception
     */
    public static void parseURL2PDFFile(String pdfDir, String url) throws Exception {
        try {
            long begin = System.currentTimeMillis();

            Map<String, String> map = extractUrlInfo(url);
            String title = map.get("title");
            if (StringUtils.isBlank(title)) {
                title = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            }
            title = wipeWindowsSpecialChars(title);
            String html = map.get("html");
            String href = map.get("href");

            Document document = new Document();
            File pdfFile = new File(pdfDir + "\\开源世界旅行手册子文件\\" + title + ".pdf");
            if (!pdfFile.getParentFile().exists()) {
                pdfFile.getParentFile().mkdirs();
            }
            PdfWriter pdfwriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            pdfwriter.setViewerPreferences(PdfWriter.HideToolbar);
            document.open();

            XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, parse2Stream(html));

//        String urlInfo = extractUrlInfo2(url);
//        XMLWorkerHelper.getInstance().parseXHtml(pdfwriter, document, parse2Stream(urlInfo));

//        BufferedWriter bw = new BufferedWriter(new FileWriter("D:/开源世界旅行手册/开源世界旅行手册ch22s05.html"));
//        bw.write(urlInfo[0]);
//        bw.close();

            document.close();

            System.out.println("一次时间：" + (System.currentTimeMillis() - begin) / 1000 + "秒");

            pdfFileList.add(pdfFile);
            if (StringUtils.isNotBlank(href)) {
                parseURL2PDFFile(pdfDir, href);
            }
        } catch (Exception e) {
            System.out.println("url==" + url);
             e.printStackTrace();
        }

    }

    private static String[] windowsSpecialChars = new String[]{"\\", "/", ":", "*", "?", "\"", "<", ">", "|"};

    public static String wipeWindowsSpecialChars(String str) {
        List<String> specialCharList = Arrays.asList(windowsSpecialChars);
        for (String specialChar : specialCharList) {
            if (str.contains(specialChar)) {
                str = str.replace(specialChar, "-");
            }
        }
        return str;
    }

    @Test
    public void test1() {
        String str = "第 1 章 GUI? CLI?.pdf";
        str = "safsasfasfsaf @dasdsa ";
        String s = wipeWindowsSpecialChars(str);
        System.out.println(s);
    }


    /**
     * 根据URL获取网页的内容，返回结果
     *
     * @param url
     * @return String[] 0:当前url页面内容，1:下一页url
     * @throws Exception
     */
    public static Map<String, String> extractUrlInfo(String url) {
        Map<String, String> map = new HashMap<>();
        //报错：Exception in thread "main" org.jsoup.HttpStatusException:HTTP error fetching URL. Status=403, URL=http://blog.csdn.net/u014520797/article/details/50944998/
        //org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
        org.jsoup.nodes.Document doc = null;
        int count = 0;
        while (doc == null) {
            count++;
            try {
//                url = "https://i.linuxtoy.org/docs/guide/ch15s03.html";
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("count=" + count);
//      爬取某个网站太快，会被封。于是要模拟像人一样的取爬取某个网站，那样的话估计几秒爬取一个网页
//      参考http://blog.sina.com.cn/s/blog_664fdc7e0102vesz.html
//        org.jsoup.nodes.Document doc = Jsoup.connect(blogURL).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(1000 * 5).get();
//        Connection connection = Jsoup.connect(blogURL).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(1000 * 5);
//        org.jsoup.nodes.Document doc = connection.get();

        org.jsoup.nodes.Element head = doc.head();
        /*try {
            BufferedReader br = new BufferedReader(new FileReader("D:\\开源世界旅行手册\\开源世界旅行手册_files\\docbook.css"));
            String line = null;
            StringBuilder builder = new StringBuilder();
            builder.append("<style type=\"text/css\">");
            while ((line = br.readLine()) != null) {
                builder.append(line);
                builder.append("\r\n");
            }
            builder.append("</style>");
            head.append(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        head.append("<style>body{font-family:SimSun;}</style>");
        String href_prefix = "https://i.linuxtoy.org/docs/guide/";
        //a标签链接补全
        ListIterator<Element> listIterator = doc.getElementsByTag("a").listIterator();
        while (listIterator.hasNext()) {
            Element element = listIterator.next();
            String href = href_prefix + element.attr("href");
            element.attr("href", href);
        }
        //img标签链接补全
        ListIterator<Element> imgListIterator = doc.getElementsByTag("img").listIterator();
        while (imgListIterator.hasNext()) {
            Element element = imgListIterator.next();
            String href = href_prefix + element.attr("src");
            element.attr("src", href);
        }
        String html = doc.html();
        map.put("html", html);
        Element element = doc.select("a[accesskey=n]").first();
        if (element != null) {
            String href = element.attr("href");
            map.put("href", href);
        }
        Element titleElement = doc.getElementsByTag("title").first();
        String title = "";
        if (titleElement.hasText()) {
            title = titleElement.text();
        } else {
            Element tbodyElement = doc.select("body>div.navheader>table>tbody").first();
            Element thElement = tbodyElement.child(0).child(0);
            if (thElement.hasText()) {
                title = thElement.text();
            } else {
                Element child = tbodyElement.child(1).child(1);
                if (child.hasText()) {
                    title = child.text();
                }
            }
        }
        map.put("title", title);
        return map;
    }

    /**
     * 根据URL获取网页的内容，返回结果
     *
     * @param url
     * @return String
     * @throws Exception
     */
    public static String extractUrlInfo2(String url) {
        StringBuilder sb = new StringBuilder();
        org.jsoup.nodes.Document doc = null;
        int count = 0;
        while (doc == null) {
            count++;
            try {
                doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").get();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("count=" + count);

        org.jsoup.nodes.Element head = doc.head();
        head.append("<style>body{font-family:SimSun;}</style>");
        ListIterator<Element> listIterator = doc.getElementsByTag("a").listIterator();
        String href_prefix = "https://i.linuxtoy.org/docs/guide/";
        while (listIterator.hasNext()) {
            Element element = listIterator.next();
            String href = href_prefix + element.attr("href");
            element.attr("href", href);
        }
        String html = doc.html();
        sb.append(html);
        Element element = doc.select("a[accesskey=n]").first();
        if (element != null) {
            String href = element.attr("href");
            String content = extractUrlInfo2(href);
            sb.append(content);
        }
        return sb.toString();
    }

    /**
     * 把String 转为 InputStream
     *
     * @param content
     * @return
     */
    public static InputStream parse2Stream(String content) {
        try {
            ByteArrayInputStream stream = new ByteArrayInputStream(content.getBytes("utf-8"));
            return stream;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
