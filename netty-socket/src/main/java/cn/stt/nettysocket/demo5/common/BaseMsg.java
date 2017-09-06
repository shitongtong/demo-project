package cn.stt.nettysocket.demo5.common;

import java.io.Serializable;

/**
 * Message基类：
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
//必须实现序列,serialVersionUID 一定要有,否者在netty消息序列化反序列化会有问题，接收不到消息！！！
public class BaseMsg implements Serializable {
    private static final long serialVersionUID = 1L;
    private int type;   //1:ping    2:login

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
