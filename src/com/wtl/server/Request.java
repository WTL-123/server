package com.wtl.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.*;

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
    //存储参数
    private Map<String,List<String>> parameterMap;
    private final String CRLF="\r\n";
    public Request(Socket client) throws IOException {
        this(client.getInputStream());
    }
    public Request(InputStream is){
        parameterMap=new HashMap<String,List<String>>();
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
        this.method=this.method.trim();
        System.out.println(method);
        System.out.println("---2、获取请求url：第一个/ 到HTTP/---");
        System.out.println("---可能包含请求参数？前面的为url---");
        //1)、获取/的位置
        int startIdx=this.requestInfo.indexOf("/")+1;
        //2)、获取HTTP/的位置
        int endIdx=this.requestInfo.indexOf("HTTP/");
        //3)、分割字符串
        this.url=this.requestInfo.substring(startIdx,endIdx).trim();
        //3)、获取？的位置
        int queryIdx=this.url.indexOf("?");
        if (queryIdx>=0){//表示存在请求参数
            String[] urlArray=this.url.split("\\?");
            this.url=urlArray[0];
            queryStr=urlArray[1];
        }
        System.out.println(url);
        System.out.println("---3、获取请求参数：如果Get已经获取，如果是post可能在请求体中---");
        if (method.equals("post")){
            String qStr=this.requestInfo.substring(this.requestInfo.lastIndexOf("CRLF")).trim();
            if (null==queryStr){
                queryStr=qStr;
            }else {
                queryStr+="&"+qStr;
            }
        }
        queryStr=null==queryStr?"":queryStr;
        System.out.println(method+"-->"+url+"-->"+queryStr);
        //转成Map
        convertMap();
    }
    //处理请求参数为map
    private void convertMap() {
        //1、分割字符串&
        String[] KeyValues = this.queryStr.split("&");
        for (String query : KeyValues) {
            //2、再次分割字符串=
            String[] kv = query.split("=");
            kv = Arrays.copyOf(kv, 2);
            //获取key和value
            String key = kv[0];
            String value = kv[1]==null?null:decode(kv[1],"UTF-8");
            //存储到map中
            if (!parameterMap.containsKey(key)) {//第一次
                parameterMap.put(key, new ArrayList<String>());
            }
            parameterMap.get(key).add(value);
        }
    }

    /**
     * 处理中文
     * @return
     */
    private String decode(String value,String enc){
        try {
            return java.net.URLDecoder.decode(value,enc);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过name获取对应的多个值
     * @param key
     * @return
     */
    public String[] getParameterValues(String key){
        List<String> list=this.parameterMap.get(key);
        if (null==list||list.size()<1){
            return null;
        }
        return list.toArray(new String[0]);
    }

    /**
     * 通过name获取对应的一个值
     * @param key
     * @return
     */
    public String getParameter(String key){
        String[] values=getParameterValues(key);
        return values==null?null:values[0];
    }
    public String getMethod() {
        return method;
    }
    public String getUrl() {
        return url;
    }
    public String getQueryStr() {
        return queryStr;
    }
}
