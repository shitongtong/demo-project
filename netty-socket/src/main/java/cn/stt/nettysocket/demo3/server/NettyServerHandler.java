package cn.stt.nettysocket.demo3.server;

import cn.stt.nettysocket.demo3.common.AskMsg;
import cn.stt.nettysocket.demo3.common.BaseMsg;
import cn.stt.nettysocket.demo3.common.LoginMsg;
import cn.stt.nettysocket.demo3.common.MsgType;
import cn.stt.nettysocket.demo3.common.PingMsg;
import cn.stt.nettysocket.demo3.common.ReplyClientBody;
import cn.stt.nettysocket.demo3.common.ReplyMsg;
import cn.stt.nettysocket.demo3.common.ReplyServerBody;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.concurrent.TimeUnit;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //channel失效，从Map中移除
        NettyChannelMap.remove((SocketChannel) ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        System.out.println("msg=" + msg);
        BaseMsg baseMsg = JSON.parseObject(msg, BaseMsg.class);
        if (MsgType.LOGIN.name().equals(baseMsg.getType())) {
            LoginMsg loginMsg = JSON.parseObject(msg, LoginMsg.class);
            if ("robin".equals(loginMsg.getUserName()) && "yao".equals(loginMsg.getPassword())) {
                //登录成功,把channel存到服务端的map中
                NettyChannelMap.add(loginMsg.getClientId(), (SocketChannel) channelHandlerContext.channel());
                System.out.println("client" + loginMsg.getClientId() + " 登录成功");
            }
        } else {
            if (NettyChannelMap.get(baseMsg.getClientId()) == null) {
                //说明未登录，或者连接断了，服务器向客户端发起登录请求，让客户端重新登录
                LoginMsg loginMsg = new LoginMsg();
                channelHandlerContext.channel().writeAndFlush(JSON.toJSONString(loginMsg));
            }
        }
        switch (baseMsg.getType()) {
            case "PING": {
                PingMsg pingMsg = JSON.parseObject(msg, PingMsg.class);
                PingMsg replyPing = new PingMsg();
                NettyChannelMap.get(pingMsg.getClientId()).writeAndFlush(JSON.toJSONString(replyPing));
            }
            break;
            case "ASK": {
                //收到客户端的请求
                AskMsg askMsg = JSON.parseObject(msg, AskMsg.class);
                if ("authToken".equals(askMsg.getParams().getAuth())) {
                    ReplyServerBody replyBody = new ReplyServerBody("server info $$$$ !!!");
                    ReplyMsg replyMsg = new ReplyMsg();
                    replyMsg.setBody(replyBody);
                    NettyChannelMap.get(askMsg.getClientId()).writeAndFlush(JSON.toJSONString(replyMsg));
                }
            }
            break;
            case "REPLY": {
                //收到客户端
                ReplyMsg replyMsg = JSON.parseObject(msg, ReplyMsg.class);
                ReplyClientBody clientBody = (ReplyClientBody) replyMsg.getBody();
                System.out.println("receive client msg: " + clientBody.getClientInfo());
            }
            break;
            default:
                break;
        }

        TimeUnit.SECONDS.sleep(5);
        NettyChannelMap.get(baseMsg.getClientId()).writeAndFlush("5s:xxx");

        ReferenceCountUtil.release(baseMsg);
    }
}
