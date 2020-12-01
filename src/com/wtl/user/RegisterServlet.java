package com.wtl.user;

import com.wtl.server.core.Request;
import com.wtl.server.core.Response;
import com.wtl.server.core.Servlet;

public class RegisterServlet implements Servlet {
    @Override
    public void service(Request request, Response response) {
        response.print("<html>");
        response.print("<head>");
        response.print("<meta charset=\"UTF-8\">");
        response.print("<title>");
        response.print("注册成功页面");
        response.print("</title>");
        response.print("</head>");
        response.print("<body>");
        response.print("注册成功");
        response.print("</body>");
        response.print("</html>");
    }
}
