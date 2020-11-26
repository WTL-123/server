package com.wtl.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 封装请求协议：获取method uri以及请求参数
 */
public class Request {
    private String requestInfo;
    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }
    public Request(InputStream is){
        byte[] datas=new byte[1024*1024];
        int len;
        try {
            len = is.read(datas);
            requestInfo = new String(datas, 0, len);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
//分解字符串
        parseRequestInfo();
    }
    private void parseRequestInfo(){
        System.out.println("-----分解-----");
        System.out.println(requestInfo);
    }
}
