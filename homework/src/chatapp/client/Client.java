package chatapp.client;

import chatapp.common.constant.MsgConstant;
import chatapp.common.entity.User;
import ui.HomeUI;

import java.net.Socket;

public class Client {
    private static Socket socket;
    private static User currentUser; // 保存当前登录用户


    public static Socket getSocket() {
        return socket;
    }

    public static void setSocket(){
        try {
            socket = new Socket(MsgConstant.IP,MsgConstant.PORT);
            System.out.println("连接服务端成功！");
        } catch (Exception e) {
            System.out.println("连接服务端失败：" + e.getMessage());
            System.exit(0);
        }
    }
    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static void main(String[] args) {
        new HomeUI().start(); // 启动主界面
    }
}