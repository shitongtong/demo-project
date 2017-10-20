package cn.stt.url2pdf.merge;

import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static cn.stt.url2pdf.merge.MergeUtil.merge;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/10/9.
 */
public class Demo {

    public static void main(String[] args) throws Exception {

    }

    /**
     * 合并:  物流管理：供应链过程的一体化
     * @deprecated 失败：java.lang.IllegalArgumentException: PdfReader not opened with owner password
     * @throws Exception
     */
    @Test
    public void test2() throws Exception {
        String basePath = "D:\\BaiduNetdiskDownload\\所有pdf文件\\杂\\物流管理：供应链过程的一体化";
        File baseDir = new File(basePath);
        List<File> mergeFileList = Arrays.asList(baseDir.listFiles());
        File outFile = new File(basePath, "物流管理：供应链过程的一体化.pdf");
        merge(mergeFileList, outFile);
    }

    /**
     * 合并hadoop笔记
     *
     * @throws Exception
     */
    @Test
    public void test1() throws Exception {
        String basePath = "D:\\BaiduNetdiskDownload\\所有pdf文件\\temp";
        File baseDir = new File(basePath);
        List<File> mergeFileList = Arrays.asList(baseDir.listFiles());
        File outFile = new File(basePath, "hadoop笔记.pdf");
        merge(mergeFileList, outFile);
    }
}
