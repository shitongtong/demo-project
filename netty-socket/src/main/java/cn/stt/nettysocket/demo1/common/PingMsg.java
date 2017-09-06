package cn.stt.nettysocket.demo1.common;

/**
 * 心跳检测Ping类型消息：
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class PingMsg extends BaseMsg {
    public PingMsg() {
        super();
        setType(MsgType.PING);
    }

    @Override
    public String toString() {
        return "PingMsg{} " + super.toString();
    }
}
