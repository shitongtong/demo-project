package cn.stt.nettysocket.demo5.client;

import cn.stt.nettysocket.demo5.protobuf.LoginProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<LoginProto.Login> {

    //利用写空闲发送心跳检测消息
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case WRITER_IDLE:
                    System.out.println("send ping to server-");
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginProto.Login baseMsg) throws Exception {
        System.out.println("baseMsg==" + baseMsg);
//        ReferenceCountUtil.release(baseMsg);
    }
}
