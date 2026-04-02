package chatapp.server;

import chatapp.server.handler.ClientHandler;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    // 线程安全的集合，存储所有在线客户端Handler（用于消息转发）
    public static CopyOnWriteArrayList<ClientHandler> onlineClients = new CopyOnWriteArrayList<>();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8888)) {
            System.out.println("服务端已启动，端口8888，等待客户端连接...");
            while (true) {
                // 阻塞等待客户端连接，每来一个连接创建一个线程处理
                Socket clientSocket = serverSocket.accept();
                System.out.println("新客户端连接：" + clientSocket.getInetAddress());
                // 创建ClientHandler处理该客户端的消息
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                // 添加到在线客户端列表
                onlineClients.add(clientHandler);
                // 启动线程
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}