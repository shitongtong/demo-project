package cn.stt.nettysocket.demo4.common;

import java.io.Serializable;

/**
 * 相应类型body对像
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class ReplyMsg implements Serializable {
    private static final long serialVersionUID = 1L;

    private int reply;  //0:建立连接    1:ping    2:login     3:t     4:数据格式不对异常

    public ReplyMsg(int reply) {
        this.reply = reply;
    }

    public int getReply() {
        return reply;
    }

    public void setReply(int reply) {
        this.reply = reply;
    }
}
