package ui;

import chatapp.client.Client;
import chatapp.client.handler.ClientMsgHandler;
import chatapp.common.constant.MsgConstant;
import chatapp.common.entity.User;
import ui.base.BaseUI;

import java.io.BufferedOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ChatUI extends BaseUI {

    public void start() {
        try {
            Socket socket = Client.getSocket();
            User currentUser = Client.getCurrentUser();
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());

            // 启动线程：实时接收服务端转发的消息
            new Thread(new ClientMsgHandler()).start();

            title("群聊大厅（输入 886 退出）");

            Scanner sc = new Scanner(System.in);
            String msg;
            while (true) {
                System.out.print("我：");
                msg = sc.nextLine().trim();

                if ("886".equals(msg)) {
                    System.out.println("退出聊天室！");
                    socket.close();
                    break;
                }
                if (msg.isEmpty()) continue;

                // 发送聊天消息（协议：2 用户名:内容）
                String sendMsg = MsgConstant.MSG_TYPE_CHAT + " " +
                        currentUser.getUsername() + ":" + msg;

                bos.write((sendMsg + "\n").getBytes());
                bos.flush();
            }

        } catch (Exception e) {
            System.out.println("聊天异常：连接已断开");
        }
    }
}