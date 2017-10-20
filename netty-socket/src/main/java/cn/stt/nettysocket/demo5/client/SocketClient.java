package cn.stt.nettysocket.demo5.client;

import cn.stt.nettysocket.demo5.protobuf.LoginProto;
import cn.stt.nettysocket.demo5.protobuf.SocketDemo;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
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
        Socket socket = new Socket("192.168.12.228", 30000);
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
    public void send3() throws Exception {
//        Thread.sleep(1000*60);
        String ip = "192.168.1.219";
//        ip = "101.132.116.184";
        ip="106.14.76.62";
        Socket socket = new Socket(ip, 30000);
//        Thread.sleep(1000*30);
        SocketDemo.Login build = SocketDemo.Login.newBuilder().setPhone("13916593205").setType(2).build();
        System.out.println(build.getSerializedSize());
        System.out.println(build.toString().length());
        build.writeDelimitedTo(socket.getOutputStream());

//        Thread.sleep(1000*60);

        SocketDemo.Login parseFrom = SocketDemo.Login.parseDelimitedFrom(socket.getInputStream());
        System.out.println(parseFrom);

        System.out.println("----------------");

        SocketDemo.Login build1 = SocketDemo.Login.newBuilder().setPhone("13916593205").setType(2).build();
        build1.writeDelimitedTo(socket.getOutputStream());
        SocketDemo.Login parseFrom1 = SocketDemo.Login.parseDelimitedFrom(socket.getInputStream());
        System.out.println(parseFrom1);

        /*
        //2.获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
        pw.write("用户名：ClientA;密码：789");
        pw.flush();
        socket.shutdownOutput();//关闭输出流

        InputStream is;
        BufferedReader br = null;
        //3.获取输入流，并读取服务器端的响应信息
        is = socket.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while ((info = br.readLine()) != null) {
            System.out.println("我是客户端A，服务器说：" + info);
        }
        //4.关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();*/

        //4.关闭资源
        socket.close();
    }

    @Test
    public void sendString() throws Exception{
        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket = new Socket("localhost", 18888);
        //2.获取输出流，向服务器端发送信息
        OutputStream os = socket.getOutputStream();//字节输出流
        PrintWriter pw = new PrintWriter(os);//将输出流包装为打印流
        pw.write("用户名：ClientA;密码：789");
        pw.flush();
        socket.shutdownOutput();//关闭输出流

        InputStream is;
        BufferedReader br = null;
        //3.获取输入流，并读取服务器端的响应信息
        is = socket.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        String info = null;
        while ((info = br.readLine()) != null) {
            System.out.println("我是客户端A，服务器说：" + info);
        }
        //4.关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();

        socket.close();
    }

    @Test
    public void send4() throws IOException {
        ServerSocket server = new ServerSocket(30000);
        Socket client = server.accept();
        InputStream inStream = client.getInputStream();
        /*BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String info = null;
        while ((info = br.readLine()) != null) {//循环读取客户端的信息
            System.out.println("我是服务器，客户端说：" + info);
            sb.append(info);
        }
        System.out.println("sb==" + sb.toString());*/
//        Parser<LoginProto.Login> loginParser = LoginProto.Login.parser();

        LoginProto.Login login = LoginProto.Login.parseDelimitedFrom(inStream);
        System.out.println("||" + login.getPhone() + "||" + login.getType() + "||" + login.getReply() + "||");
        client.shutdownInput();//关闭输入流
        client.close();
        server.close();
    }

    @Test
    public void send5() throws Exception{
        String ip = "192.168.1.219";
//        ip = "101.132.116.184";
        Socket socket = new Socket(ip, 30000);

        ClientThread clientThread = new ClientThread(socket);
        Thread thread = new Thread(clientThread);
        thread.start();

        ClientThread clientThread2 = new ClientThread(socket);
        Thread thread2 = new Thread(clientThread2);
        thread2.start();
    }

}
