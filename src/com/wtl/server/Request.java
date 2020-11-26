package com.wtl.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * 封装请求协议：获取method uri以及请求参数
 */
public class Request {
    //请求信息
    private String requestInfo;
    //请求方式
    private String method;
    //请求url
    private String url;
    //请求参数
    private String queryStr;
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
        System.out.println("---1、获取请求方式：开头到第一个/---");
        this.method=this.requestInfo.substring(0,this.requestInfo.indexOf("/")).toLowerCase();
        System.out.println(method);
        System.out.println("---1、获取请求url：第一个/ 到HTTP/---");
        System.out.println("---可能包含请求参数？前面的为url---");
        //1)、获取/的位置
        int startIdx=this.requestInfo.indexOf("/")+1;
        //2)、获取HTTP/的位置
        int endIdx=this.requestInfo.indexOf("HTTP/");
        //3)、分割字符串
        this.url=this.requestInfo.substring(startIdx,endIdx);
        //3)、获取？的位置
        int queryIdx=this.url.indexOf("?");
        if (queryIdx>0){//表示存在请求参数
            String[] urlArray=this.url.split("\\?");
            this.url=urlArray[0];
            queryStr=urlArray[1];
        }
        System.out.println(url);
    }
}
