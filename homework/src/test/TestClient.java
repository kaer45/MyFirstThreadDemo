package test;

import chatapp.common.constant.MsgConstant;
import chatapp.common.entity.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class TestClient {
    public static void main(String[] args) {
        try {
            // 连接服务器
            Socket socket = new Socket("127.0.0.1", 8888);
            System.out.println("连接服务器成功！");

            // 准备输入输出流
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // 测试注册功能
            System.out.println("=== 测试注册功能 ===");
            String registerMsg = MsgConstant.MSG_TYPE_REGISTER + " " + "username=test&password=Test123!";
            System.out.println("发送注册请求: " + registerMsg);
            bw.write(registerMsg + "\n");
            bw.flush();

            // 接收注册反馈
            String registerFeedback = br.readLine();
            System.out.println("注册反馈: " + (registerFeedback.equals("1") ? "成功" : "失败"));

            // 测试登录功能
            System.out.println("\n=== 测试登录功能 ===");
            String loginMsg = MsgConstant.MSG_TYPE_LOGIN + " " + "username=test&password=Test123!";
            System.out.println("发送登录请求: " + loginMsg);
            bw.write(loginMsg + "\n");
            bw.flush();

            // 接收登录反馈
            String loginFeedback = br.readLine();
            System.out.println("登录反馈: " + (loginFeedback.equals("1") ? "成功" : "失败"));

            // 测试聊天功能
            System.out.println("\n=== 测试聊天功能 ===");
            String chatMsg = MsgConstant.MSG_TYPE_CHAT + " " + "test: 大家好！";
            System.out.println("发送聊天消息: " + chatMsg);
            bw.write(chatMsg + "\n");
            bw.flush();

            // 关闭资源
            br.close();
            bw.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}