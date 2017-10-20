package cn.stt.nettysocket.demo5.client;

import cn.stt.nettysocket.demo5.protobuf.SocketDemo;

import java.io.IOException;
import java.net.Socket;

/**
 * 服务器线程处理类
 *
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/1.
 */
public class ClientThread implements Runnable {
    // 和本线程相关的Socket
    Socket socket = null;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    //线程执行的操作，响应客户端的请求
    @Override
    public void run() {
        System.out.println("|||||||||||||||");
        SocketDemo.Login build = SocketDemo.Login.newBuilder().setPhone("13916593205").setType(2).build();
        try {
            build.writeDelimitedTo(socket.getOutputStream());
            SocketDemo.Login parseFrom = SocketDemo.Login.parseDelimitedFrom(socket.getInputStream());
            System.out.println(parseFrom);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
