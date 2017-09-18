package cn.stt.nettysocket.demo5.client;

import cn.stt.nettysocket.demo5.protobuf.LoginProto;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyClientBootstrap {
    private int port;
    private String host;
    private SocketChannel socketChannel;
    private static final EventExecutorGroup group = new DefaultEventExecutorGroup(20);

    public NettyClientBootstrap(int port, String host) throws InterruptedException {
        this.port = port;
        this.host = host;
        start();
    }

    private void start() throws InterruptedException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(host, port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline()
//                        .addLast(new IdleStateHandler(20, 10, 0))
                        .addLast(new ProtobufVarint32FrameDecoder())
                        .addLast(new ProtobufDecoder(LoginProto.Login.getDefaultInstance()))
                        .addLast(new ProtobufVarint32LengthFieldPrepender())
                        .addLast(new ProtobufEncoder())
                        .addLast(new NettyClientHandler());
            }
        });
        ChannelFuture future = bootstrap.connect(host, port).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            System.out.println("connect server  成功");
        }
    }



    public static void main(String[] args) throws InterruptedException {
        NettyClientBootstrap bootstrap = new NettyClientBootstrap(30000, "localhost");
//        NettyClientBootstrap bootstrap = new NettyClientBootstrap(30000, "localhost");
//        bootstrap.socketChannel.writeAndFlush(Timestamp.newBuilder().setSeconds(8767868));
        bootstrap.socketChannel.writeAndFlush(LoginProto.Login.newBuilder().setPhone("1234511254").setType(2).build());
//        bootstrap.socketChannel.writeAndFlush(PersonProto.Person.newBuilder().setPhone("dsddsfs").setType(2).build());
//        bootstrap.socketChannel.writeAndFlush(SocketDemo.Login.newBuilder().setPhone("dsddsfs").setType(2).build());
//        bootstrap.socketChannel.writeAndFlush(SocketDemo.Login.newBuilder());
//        bootstrap.socketChannel.writeAndFlush("ssss");
//        System.out.println(LoginProto.Login.newBuilder().setPhone("dsddsfs").setType(1).build().getSerializedSize());
//        System.out.println(Timestamp.getDefaultInstance().getSeconds());
//        System.out.println(PersonProto.Person.getDefaultInstance().getPhone());
    }
}
