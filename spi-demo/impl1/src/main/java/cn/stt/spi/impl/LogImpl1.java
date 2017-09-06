package cn.stt.spi.impl;

import cn.stt.spi.api.Log;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/8/18.
 */
public class LogImpl1 implements Log {
    public void write(String content) {
        System.out.println(content + " 写入文件1");
    }
}
