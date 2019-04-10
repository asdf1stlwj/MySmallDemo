package com.asdf1st.socketserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args) throws IOException {
        //创建一个ServerSocket用于监听客户端的请求
        ServerSocket serverSocket = new ServerSocket(3000);
        System.out.println("Server Start!");
        while (true) {
            //当接收到客户端的请求时，产生一个对应的Socket
            Socket socket = serverSocket.accept();
            InputStream is=socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            os.write("msg from server:asdf1st \n".getBytes("utf-8"));
            os.close();
            socket.close();
        }

    }

}
