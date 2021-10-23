package com.ypl;

/**
 * @Author 叶佩林
 * @Date 2021/10/22 18:01
 * @Version 1.0
 */
public class StartThread extends Thread {
    @Override
    public void run() {
        try {
            TCPServer server = new TCPServer();
            server.upload();// 启动开启服务，监听
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
