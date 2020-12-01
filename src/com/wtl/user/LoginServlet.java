package com.wtl.user;

import com.wtl.server.core.Request;
import com.wtl.server.core.Response;
import com.wtl.server.core.Servlet;

public class LoginServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        //关注了内容
        response.print("<html>");
        response.print("<head>");
        response.print("<meta charset=\"UTF-8\">");
        response.print("<title>");
        response.print("登录成功页面");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("欢迎回来："+request.getParameter("name"));
        response.print("</body>");
        response.print("</html>");
    }
}
