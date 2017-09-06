package cn.stt.nettysocket.demo3.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyServerBootstrap {
    private int port;
    private SocketChannel socketChannel;

    public NettyServerBootstrap(int port) throws InterruptedException {
        this.port = port;
        bind();
    }

    private void bind() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, worker);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.option(ChannelOption.SO_BACKLOG, 128);
        //通过NoDelay禁用Nagle,使消息立即发出去，不用等待到一定的数据量才发出去
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        //保持长连接状态
        bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline p = socketChannel.pipeline();
                //超时时间  貌似没什么用？
//                p.addLast(new IdleStateHandler(5,5,5, TimeUnit.SECONDS));
                // 以("\n")为结尾分割的 解码器
//                p.addLast("framer", new DelimiterBasedFrameDecoder(8192, Delimiters.lineDelimiter()));
                // 字符串解码 和 编码
                p.addLast(new StringDecoder());
                p.addLast(new StringEncoder());
                // 自己的逻辑Handler
                p.addLast(new NettyServerHandler());
            }
        });
        ChannelFuture f = bootstrap.bind(port).sync();
        if (f.isSuccess()) {
            System.out.println("server start");
        }
        // 等待客户端的关闭
        Channel channel = f.channel();
        // 要记得是closeFuture()，作用是等待服务端的关闭
        channel.closeFuture().sync();
        // 该条语句不会输出，原因:上面没有关闭...
        // System.out.println("客户端已经关闭...");
    }

    public static void main(String[] args) throws InterruptedException {
        NettyServerBootstrap bootstrap = new NettyServerBootstrap(29999);
        /*while (true) {
            SocketChannel channel = (SocketChannel) NettyChannelMap.get("001");
            if (channel != null) {
                AskMsg askMsg = new AskMsg();
                channel.writeAndFlush(askMsg);
            }
            TimeUnit.SECONDS.sleep(5);
        }*/
    }
}
