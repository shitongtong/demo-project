package cn.stt.voice;

import com.iflytek.cloud.speech.SpeechConstant;
import com.iflytek.cloud.speech.SpeechError;
import com.iflytek.cloud.speech.SpeechSynthesizer;
import com.iflytek.cloud.speech.SpeechUtility;
import com.iflytek.cloud.speech.SynthesizeToUriListener;
import com.iflytek.cloud.speech.SynthesizerListener;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/10/20.
 */
public class VoiceSynthesizer {
    /*public static void main(String[] args) {
        try {
            synthesizerPlay();
//            synthesizerFile();
        }catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public static void synthesizerFile() {
        // 将“XXXXXXXX”替换成您申请的APPID
        SpeechUtility.createUtility( SpeechConstant.APPID +"=59e955dd ");//59e955dd
        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts= SpeechSynthesizer.createSynthesizer( );
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速，范围0~100
        mTts.setParameter(SpeechConstant.PITCH, "50");//设置语调，范围0~100
        mTts.setParameter(SpeechConstant.VOLUME, "50");//设置音量，范围0~100


        //合成监听器
        SynthesizeToUriListener synthesizeToUriListener = new SynthesizeToUriListener() {
            //progress为合成进度0~100
            public void onBufferProgress(int progress) {}
            //会话合成完成回调接口
            //uri为合成保存地址，error为错误信息，为null时表示合成会话成功
            public void onSynthesizeCompleted(String uri, SpeechError error) {
                System.out.println("---onSynthesizeCompleted start---");
                System.out.println(uri);
                System.out.println(error);
                System.out.println("---onSynthesizeCompleted end---");
            }

            @Override
            public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {

            }
        };
        String filePath = "/home/only/tts_test_synthesizerFile.mp3";
        //3.开始合成
        //设置合成音频保存位置（可自定义保存位置），默认保存在“./tts_test.pcm”
        mTts.synthesizeToUri("语音合成测试程序", filePath,synthesizeToUriListener);
    }

    public static void synthesizerPlay() {
        // 将“XXXXXXXX”替换成您申请的APPID
        SpeechUtility.createUtility( SpeechConstant.APPID +"=59e955dd ");//59e955dd
        //1.创建SpeechSynthesizer对象
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer();
        //2.合成参数设置，详见《MSC Reference Manual》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        //设置合成音频保存位置（可自定义保存位置），保存在“./tts_test.pcm”
        String filePath = "./tts_test_synthesizerPlay.mp3"; //pcm
        //如果不需要保存合成音频，注释该行代码
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, filePath);

        //合成监听器
        SynthesizerListener mSynListener = new SynthesizerListener() {
            //会话结束回调接口，没有错误时，error为null
            public void onCompleted(SpeechError error) {
                System.out.println("---onCompleted start---");
                System.out.println(error);
                System.out.println("---onCompleted end---");
            }

            @Override
            public void onEvent(int i, int i1, int i2, int i3, Object o, Object o1) {
                System.out.println("onEvent");
            }

            //缓冲进度回调
            //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
            public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
                System.out.println("onBufferProgress");
            }

            //开始播放
            public void onSpeakBegin() {
                System.out.println("onSpeakBegin");
            }

            //暂停播放
            public void onSpeakPaused() {
                System.out.println("onSpeakPaused");
            }

            //播放进度回调
            //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
            public void onSpeakProgress(int percent, int beginPos, int endPos) {
                System.out.println("onSpeakProgress");
            }

            //恢复播放回调接口
            public void onSpeakResumed() {
                System.out.println("onSpeakResumed");
            }
        };

        //3.开始合成
        mTts.startSpeaking("语音合成测试程序", mSynListener);
    }
}
