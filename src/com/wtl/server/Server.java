package com.wtl.server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 使用ServerSocket建立与浏览器的连接，获取请求协议
 */
public class Server {
    private ServerSocket serverSocket;
    public static void main(String[] args) {
        Server server=new Server();
        server.start();
    }
//    启动服务器
    public void start(){
        try {
            serverSocket=new ServerSocket(8888);
            receive();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败...");
        }
    }
//    接收连接处理
    public void receive(){
        try {
            Socket client = serverSocket.accept();
            System.out.println("一个客户端建立了连接...");
//获取请求协议
            InputStream is = client.getInputStream();
            byte[] datas=new byte[1024*1024];
            int len = is.read(datas);
            String requestInfo = new String(datas, 0, len);
            System.out.println(requestInfo);
            Response response=new Response(client);
            //关注了内容
            response.print("<html>");
            response.print("<head>");
            response.print("<title>");
            response.print("服务器响应成功");
            response.print("</title>");
            response.print("</head>");
            response.print("<body>");
            response.print("终于回来了。。。");
            response.print("</body>");
            response.print("</html>");
            //关注了状态码
            response.pushToBrowser(200);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端错误");
        }
    }
//    停止服务器
    public void stop(){

    }
}
