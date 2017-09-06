package cn.stt.nettysocket.demo3.common;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class ReplyClientBody extends ReplyBody {
    private String clientInfo;

    public ReplyClientBody(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }
}
