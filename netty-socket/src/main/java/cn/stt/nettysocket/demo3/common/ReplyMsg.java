package cn.stt.nettysocket.demo3.common;

/**
 * 响应类型消息：
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class ReplyMsg extends BaseMsg {
    public ReplyMsg() {
        super();
        setType(MsgType.REPLY.name());
    }

    private ReplyBody body;

    public ReplyBody getBody() {
        return body;
    }

    public void setBody(ReplyBody body) {
        this.body = body;
    }
}
