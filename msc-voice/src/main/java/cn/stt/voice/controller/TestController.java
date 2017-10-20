package cn.stt.voice.controller;

import cn.stt.voice.VoiceSynthesizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/7/25.
 */
@RestController
@RequestMapping("/test")
public class TestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    @RequestMapping("/test")
    public void test() throws IOException {
        LOGGER.info("test");
        VoiceSynthesizer.synthesizerFile();
    }

    @RequestMapping("/test1")
    public void test1() throws IOException {
        LOGGER.info("test1");
        VoiceSynthesizer.synthesizerPlay();
    }

}
