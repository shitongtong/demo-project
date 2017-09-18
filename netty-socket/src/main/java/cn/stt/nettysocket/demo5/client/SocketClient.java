package cn.stt.nettysocket.demo5.client;

import cn.stt.nettysocket.demo5.protobuf.LoginProto;
import cn.stt.nettysocket.demo5.protobuf.SocketDemo;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * @Author shitongtong
 * <p>
 * Created by shitongtong on 2017/9/8.
 */
public class SocketClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("192.168.1.219", 30000);
        //建立输入流
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        LoginProto.Login build = LoginProto.Login.newBuilder().setPhone("45112452").setType(2).build();

        //输入对象, 一定要flush（）
        oos.writeObject(build.toString());
        oos.flush();

        oos.close();
        socket.close();
    }

    @Test
    public void send() throws IOException {
        Socket socket = new Socket("192.168.1.219", 30000);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        LoginProto.Login build = LoginProto.Login.newBuilder().setPhone("45112452").setType(2).build();
        build.writeDelimitedTo(socket.getOutputStream());
        bufferedWriter.write(build.toString());
        bufferedWriter.flush();
        bufferedWriter.close();
        socket.close();
    }

    @Test
    public void send1() throws IOException {
        Socket socket = new Socket("192.168.1.219", 30000);
        OutputStream outputStream = socket.getOutputStream();
        LoginProto.Login build = LoginProto.Login.newBuilder().setPhone("45112452").setType(2).build();
        outputStream.write(build.toByteArray());
        outputStream.flush();
        outputStream.close();
        socket.close();
    }

    @Test
    public void send2() throws IOException {
        Socket socket = new Socket("192.168.1.219", 30000);
        LoginProto.Login build = LoginProto.Login.newBuilder().setPhone("13916593205").setType(2).build();
        System.out.println(build.getSerializedSize());
        System.out.println(build.toString().length());
        build.writeDelimitedTo(socket.getOutputStream());
        LoginProto.Login parseFrom = LoginProto.Login.parseDelimitedFrom(socket.getInputStream());
        System.out.println(parseFrom);
        //4.关闭资源
        socket.close();
    }

    @Test
    public void send3() throws IOException {
        Socket socket = new Socket("192.168.1.219", 30000);
        SocketDemo.Login build = SocketDemo.Login.newBuilder().setPhone("13916593205").setType(2).build();
        System.out.println(build.getSerializedSize());
        System.out.println(build.toString().length());
        build.writeDelimitedTo(socket.getOutputStream());
        SocketDemo.Login parseFrom = SocketDemo.Login.parseDelimitedFrom(socket.getInputStream());
        System.out.println(parseFrom);
        //4.关闭资源
        socket.close();
    }
}
