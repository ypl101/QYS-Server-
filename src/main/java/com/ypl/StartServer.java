package com.ypl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @Author 叶佩林
 * @Date 2021/10/22 18:00
 * @Version 1.0
 */
public class StartServer extends HttpServlet {
    public void init() throws ServletException {

        Thread s = new StartThread();
        s.setDaemon(true);// 设置线程为后台线程，tomcat不会被hold,启动后依然一直监听。
        s.start();

    }
}
