package cn.stt.nettysocket.demo1.common;

import java.io.Serializable;

/**
 * Message基类：
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
//必须实现序列,serialVersionUID 一定要有,否者在netty消息序列化反序列化会有问题，接收不到消息！！！
public abstract class BaseMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private MsgType type;
    //必须唯一，否者会出现channel调用混乱 实际上不必要，ChannelId channelId = socketChannel.id();唯一！
    private String clientId;

    //初始化客户端id
    public BaseMsg() {
        this.clientId = Constants.getClientId();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public MsgType getType() {
        return type;
    }

    public void setType(MsgType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BaseMsg{" +
                "type=" + type +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
