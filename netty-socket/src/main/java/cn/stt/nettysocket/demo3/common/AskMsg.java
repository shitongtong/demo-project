package cn.stt.nettysocket.demo3.common;

/**请求类型消息：
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class AskMsg extends BaseMsg {
    public AskMsg() {
        super();
        setType(MsgType.ASK.name());
    }
    private AskParams params;

    public AskParams getParams() {
        return params;
    }

    public void setParams(AskParams params) {
        this.params = params;
    }
}
