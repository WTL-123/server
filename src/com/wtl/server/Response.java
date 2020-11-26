package com.wtl.server;

import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * 封装响应协议：动态获取内容，关注内容，关注响应码
 */
public class Response {
    private BufferedWriter bw;
//正文
    private StringBuilder content;
//协议头（状态行与请求头回车）信息
    private StringBuilder headinfo;
//正文的字节数
    private int len;
    private final String BLANK=" ";
    private final String CRLF="\r\n";
    private Response(){
        content=new StringBuilder();
        headinfo=new StringBuilder();
        len=0;
    }
    public Response(Socket client){
        this();
        try {
            bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            headinfo=null;
        }
    }
    public Response(OutputStream os){
        this();
        bw=new BufferedWriter(new OutputStreamWriter(os));
    }
//动态添加内容
    public Response print(String info){
        content.append(info);
        len+=info.getBytes().length;
        return this;
    }
    public Response println(String info){
        content.append(info).append(CRLF);
        len+=(info+CRLF).getBytes().length;
        return this;
    }
//推送响应信息
    public void pushToBrowser(int code) throws IOException {
        if (null==headinfo){
            code=505;
        }
        createHeadInfo(code);
        bw.append(headinfo);
        bw.append(content);
        bw.flush();
    }
//构建头信息
    private void createHeadInfo(int code){
//1、响应行：HTTP/1.1 200 OK
        headinfo.append("Http/1.1").append(BLANK);
        headinfo.append(code).append(BLANK);
        switch (code){
            case 200:
                headinfo.append("OK").append(CRLF);
                break;
            case 404:
                headinfo.append("NOT FOUND").append(CRLF);
                break;
            case 505:
                headinfo.append("SERVER ERROR").append(CRLF);
                break;
        }
//2、响应行（最后一行存在空行）
        headinfo.append("Date:").append(new Date()).append(CRLF);
        headinfo.append("Server").append("wtl Server/0.0.1;charset=GBK").append(CRLF);
        headinfo.append("Content-type;text/html").append(CRLF);
        headinfo.append("Content-length:").append(len).append(CRLF);
        headinfo.append(CRLF);
    }
}
