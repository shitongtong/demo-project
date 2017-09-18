package cn.stt.nettysocket.demo5.server;

import cn.stt.nettysocket.demo5.protobuf.LoginProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<LoginProto.Login> {

    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServerHandler.class);

    //利用读空闲读取心跳检测消息
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            switch (e.state()) {
                case READER_IDLE:
//                    PingMsg pingMsg = new PingMsg();
//                    ctx.writeAndFlush(pingMsg);
                    System.out.println("receive client ping info -");
                    break;
                default:
                    break;
            }
        }
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginProto.Login msg) throws Exception {
        LOGGER.info("msg={}", msg);
        SocketChannel socketChannel = (SocketChannel) channelHandlerContext.channel();
        String channelId = socketChannel.id().asShortText();
        socketChannel.writeAndFlush(LoginProto.Login.newBuilder().setPhone("hahah").setType(2).build());
        /*BaseMsg baseMsg = JSON.parseObject(msg, BaseMsg.class);
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
        */
    }
}
