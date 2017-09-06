package cn.stt.nettysocket.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/4.
 */
public class NettyServer2 {
    public static void main(String[] args) {
        // 得到ServerBootstrap对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        // 创建线程池
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            // 类似于Netty3中的工厂
            serverBootstrap.group(boss, worker);
            serverBootstrap.channel(NioServerSocketChannel.class);

            // 类似于Netty3中的管道
            serverBootstrap.childHandler(new ChannelInitializer<Channel>() {

                @Override
                protected void initChannel(Channel channel) throws Exception {
                    /**
                     * 将“上传下载”数据装饰成String类型
                     */
                    channel.pipeline().addLast(new StringDecoder());
                    channel.pipeline().addLast(new StringEncoder());
                    // 要处理的Handler
                    channel.pipeline().addLast(new ServerHandler2());
                }
            });

            // 设置参数，TCP参数
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 2048);// serverSocketchannel的设置，链接缓冲池的大小
            serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);// socketchannel的设置,维持链接的活跃，清除死链接
            serverBootstrap.childOption(ChannelOption.TCP_NODELAY, true);// socketchannel的设置,关闭延迟发送

            // 绑定端口，并且返回ChannelFuture对象
            ChannelFuture future = serverBootstrap.bind(new InetSocketAddress(9090));

            System.out.println("启动服务器...");

            // 等待客户端的关闭
            Channel channel = future.channel();
            // 要记得是closeFuture()，作用是等待服务端的关闭
            channel.closeFuture().sync();
            // 该条语句不会输出，原因:上面没有关闭...
            // System.out.println("客户端已经关闭...");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}

class ServerHandler2 extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext arg0, String arg1) throws Exception {
        // 输出接收到的数据
        System.out.println(arg1);

        // 向客户端发送数据
        arg0.channel().writeAndFlush("hi");
    }

    /**
     *
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        System.out.println("channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        System.out.println("channelInactive");
    }
}
