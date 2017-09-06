package cn.stt.nettysocket.demo4.common;

/**
 * 登录类型消息：
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class LoginMsg extends BaseMsg {
    private String phone;

    public LoginMsg() {
        super();
        setType(2);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
