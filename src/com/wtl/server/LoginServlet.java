package com.wtl.server;

public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        //关注了内容
        response.print("<html>");
        response.print("<head>");
        response.print("<title>");
        response.print("hgfhdgfdhgd");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("sdfasfds");
        response.print("</body>");
        response.print("</html>");
    }
}
