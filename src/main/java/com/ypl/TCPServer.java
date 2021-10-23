package com.ypl;

import javax.servlet.http.HttpServlet;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author 叶佩林
 * @Date 2021/10/22 17:58
 * @Version 1.0
 */
public class TCPServer extends HttpServlet {

    public void upload() throws Exception {

        // 创建服务器,等待客户端连接
        ServerSocket serverSocket = new ServerSocket(8082);
        System.out.println("=====================服务器端已启动,等待客户端连接====================");
        // 实现多个客户端连接服务器的操作
//        while (true) {
        final Socket clientSocket = serverSocket.accept();
        final String dectory = "E:/uploads";
        // 启动线程
        new Thread() {
            public void run() {
                OutputStream out=null;
                BufferedOutputStream fileOut=null;
                InputStream in=null;
                try {
                    // 显示哪个客户端连接上了服务器
                    // 得到ip地址对象
                    InetAddress ipAddress = clientSocket.getInetAddress();
                    // 得到ip 地址字符串
                    String ip = ipAddress.getHostAddress();
                    System.out.println("客户端IP:" + ip);
                    // 获取Socket输入流
                    in = clientSocket.getInputStream();

                    // 创建目的地的字节输出流

                    //根据日期构建文件存放目录
                    SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
                    String dectory2 = df.format(new Date());


                    // 要保存文件的绝对路径
                    String buildPath =  dectory + "/" + dectory2 + "/";
                    //目标目录不存在的话就自动创建
                    File f1 = new File(buildPath);
                    if (!f1.exists()) {
                        f1.mkdirs();//建立目录
                    }
                    //使用UUID重命名
                     String reName= UUID.randomUUID().toString();
                    fileOut = new BufferedOutputStream(new FileOutputStream(
                            f1 + reName +".png"));
                    // 把Socket输入流中的数据,写入目的地的字节输出流中
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = in.read(buffer)) != -1) {
                        // 写入目的地的字节输出流中
                        fileOut.write(buffer, 0, len);
                    }

                    // ====================反馈信息====================
                    // 获取Socket的输出流,作用:写反馈信息给客户端
                    out = clientSocket.getOutputStream();
                    // 写反馈信息给客户端
                    out.write(reName.getBytes("UTF-8"));
                    // 关闭流

                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }finally {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fileOut.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
//        }
    }
}
