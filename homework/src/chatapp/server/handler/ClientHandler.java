package chatapp.server.handler;

import chatapp.common.constant.MsgConstant;
import chatapp.common.entity.User;
import chatapp.common.util.CommonUtil;
import chatapp.server.Server;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket clientSocket;
    private BufferedReader br;
    private BufferedOutputStream bos;
    private User currentUser; // 当前登录/注册的用户

    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
        try {
            // 初始化流
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bos = new BufferedOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            closeResource(); // 初始化失败关闭资源
        }
    }

    @Override
    public void run() {
        String msg;
        try {
            // 循环读取客户端消息
            while ((msg = br.readLine()) != null) {
                // 解析消息类型（第一位）
                int msgType = Integer.parseInt(msg.substring(0, 1));
                String content = msg.substring(2); // 消息内容

                switch (msgType) {
                    case MsgConstant.MSG_TYPE_LOGIN:
                        handleLogin(content); // 处理登录
                        break;
                    case MsgConstant.MSG_TYPE_REGISTER:
                        handleRegister(content); // 处理注册
                        break;
                    case MsgConstant.MSG_TYPE_CHAT:
                        forwardChatMsg(content); // 转发聊天消息
                        break;
                    default:
                        System.out.println("未知消息类型：" + msgType);
                }
            }
        } catch (Exception e) {
            System.out.println("客户端断开连接：" + clientSocket.getInetAddress());
        } finally {
            // 客户端断开连接，从在线列表移除并关闭资源
            Server.onlineClients.remove(this);
            closeResource();
        }
    }

    // 处理登录
    private void handleLogin(String content) throws IOException {
        User user = CommonUtil.parseUser(content);
        User existUser = CommonUtil.checkUserExist(user);
        if (existUser != null && existUser.getPassword().equals(user.getPassword())) {
            this.currentUser = user; // 记录当前登录用户
            sendFeedback(MsgConstant.FEEDBACK_SUCCESS); // 登录成功
        } else {
            sendFeedback(MsgConstant.FEEDBACK_FAIL); // 登录失败
        }
    }

    // 处理注册
    private void handleRegister(String content) throws IOException {
        User user = CommonUtil.parseUser(content);
        if (CommonUtil.checkUserExist(user) == null) {
            // 用户名不存在，写入文件注册
            CommonUtil.saveUserToFile(user);
            this.currentUser = user; // 记录当前注册用户
            sendFeedback(MsgConstant.FEEDBACK_SUCCESS); // 注册成功
        } else {
            sendFeedback(MsgConstant.FEEDBACK_FAIL); // 用户名已存在
        }
    }

    // 转发聊天消息给所有在线客户端
    private void forwardChatMsg(String content) throws IOException {
        // 遍历所有在线客户端，转发消息（排除自己）
        for (ClientHandler client : Server.onlineClients) {
            if (client != this && client.currentUser != null) {
                // 发送消息到目标客户端
                client.bos.write((content + "\n").getBytes());
                client.bos.flush();
            }
        }
    }

    // 发送反馈给客户端
    private void sendFeedback(int feedback) throws IOException {
        bos.write((feedback + "\n").getBytes());
        bos.flush();
    }

    // 关闭资源
    private void closeResource() {
        try {
            if (br != null) br.close();
            if (bos != null) bos.close();
            if (clientSocket != null && !clientSocket.isClosed()) clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}