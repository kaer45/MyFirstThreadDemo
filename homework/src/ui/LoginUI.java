package ui;

import chatapp.client.Client;
import chatapp.common.constant.MsgConstant;
import chatapp.common.entity.User;
import chatapp.common.util.CommonUtil;
import ui.base.BaseUI;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class LoginUI extends BaseUI {

    public void show() {
        title("用户登录");

        System.out.print("用户名：");
        String username = sc.nextLine().trim();

        System.out.print("密码：");
        String password = sc.nextLine().trim();

        // 格式校验
        if (!CommonUtil.checkUserName(username)) {
            System.out.println("用户名格式错误！4-12位字母/数字");
            return;
        }
        if (!CommonUtil.checkPassword(password)) {
            System.out.println("密码格式错误！必须包含字母、数字、符号");
            return;
        }

        // 发送登录请求
        try {
            if(Client.getSocket()==null||Client.getSocket().isClosed()) Client.setSocket();
            Socket socket = Client.getSocket();
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 拼接协议：0 username=xxx&password=xxx
            String msg = MsgConstant.MSG_TYPE_LOGIN + " "+
                    "username=" + username + "&password=" + password;

            bos.write((msg + "\n").getBytes());
            bos.flush();

            // 接收结果
            String res = br.readLine().trim();
            if ("1".equals(res)) {
                System.out.println("登录成功！正在进入聊天室...");
                // 设置当前用户
                User user = new User(username, password);
                Client.setCurrentUser(user);
                new ChatUI().start();
            } else {
                System.out.println("登录失败：用户名或密码错误！");
            }

        } catch (Exception e) {
            System.out.println("登录异常：" + e.getMessage());
        }
    }
}