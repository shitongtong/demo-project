package cn.stt.nettysocket.demo4.server;

import cn.stt.nettysocket.demo4.common.BaseMsg;
import cn.stt.nettysocket.demo4.common.LoginMsg;
import cn.stt.nettysocket.demo4.common.ReplyMsg;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    //利用读空闲读取心跳检测消息
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
                    ReplyMsg replyMsg = new ReplyMsg(1);
                    SocketChannel socketChannel = (SocketChannel) ctx.channel();
                    ChannelFuture channelFuture = socketChannel.writeAndFlush(JSON.toJSONString(replyMsg));
                    System.out.println("channelFuture=="+channelFuture);
                    System.out.println("no receive "+socketChannel+" ping info -");
                    break;
                default:
                    System.out.println("xxxxxxxxxxxxxxx");
                    break;
            }
        }
        System.out.println("hehe==" + evt);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        LOGGER.info("连接{},异常:{}", socketChannel, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        String channelId = socketChannel.id().asShortText();
        //把channel存到服务端的map中
        NettyChannelMap.add(channelId, socketChannel);
        LOGGER.info("{}建立连接", socketChannel);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SocketChannel socketChannel = (SocketChannel) ctx.channel();
        //channel失效，从Map中移除
        String channelId = socketChannel.id().asShortText();
        NettyChannelMap.remove(channelId);
        ClientChannelMap.removeById(channelId);
        LOGGER.info("{}断开连接", socketChannel);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) throws Exception {
        LOGGER.info("msg={}", msg);
        SocketChannel socketChannel = (SocketChannel) channelHandlerContext.channel();
        String channelId = socketChannel.id().asShortText();

        BaseMsg baseMsg = JSON.parseObject(msg, BaseMsg.class);
        int type = baseMsg.getType();
        if (type == 2) {
            LoginMsg loginMsg = JSON.parseObject(msg, LoginMsg.class);
            String phone = loginMsg.getPhone();
            String oldChannelId = ClientChannelMap.get(phone);
            if (ClientChannelMap.get(phone) != null) {  //账号有登录
                //t
                ReplyMsg replyMsg = new ReplyMsg(3);
                SocketChannel channel = NettyChannelMap.get(oldChannelId);
                channel.writeAndFlush(JSON.toJSONString(replyMsg));

                ClientChannelMap.remove(phone);
            }
            ReplyMsg replyMsg = new ReplyMsg(2);
            socketChannel.writeAndFlush(JSON.toJSONString(replyMsg));

            ClientChannelMap.add(phone, channelId);
        } else if (type == 1) {
            ReplyMsg replyMsg = new ReplyMsg(1);
            socketChannel.writeAndFlush(JSON.toJSONString(replyMsg));
        }

        ReferenceCountUtil.release(baseMsg);

    }
}
