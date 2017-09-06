package cn.stt.nettysocket.demo5.client;

import com.google.protobuf.Timestamp;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Timestamp> {

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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Timestamp baseMsg) throws Exception {
        System.out.println("baseMsg==" + baseMsg);
        ReferenceCountUtil.release(baseMsg);
    }
}
