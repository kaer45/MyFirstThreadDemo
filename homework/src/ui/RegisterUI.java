package ui;

import chatapp.client.Client;
import chatapp.common.constant.MsgConstant;
import chatapp.common.util.CommonUtil;
import ui.base.BaseUI;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class RegisterUI extends BaseUI {

    public void show() {
        title("用户注册");

        System.out.print("用户名：");
        String username = sc.nextLine().trim();

        System.out.print("密码：");
        String password = sc.nextLine().trim();

        System.out.print("确认密码：");
        String rePwd = sc.nextLine().trim();

        // 校验
        if (!password.equals(rePwd)) {
            System.out.println("两次密码不一致！");
            return;
        }
        if (!CommonUtil.checkUserName(username)) {
            System.out.println("用户名格式错误！");
            return;
        }
        if (!CommonUtil.checkPassword(password)) {
            System.out.println("密码必须包含字母、数字、符号！");
            return;
        }

        try {
            if(Client.getSocket()==null||Client.getSocket().isClosed()) Client.setSocket();
            Socket socket = Client.getSocket();
            BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg = MsgConstant.MSG_TYPE_REGISTER + " " +
                    "username=" + username + "&password=" + password;

            bos.write((msg + "\n").getBytes());
            bos.flush();

            String res = br.readLine().trim();
            if ("1".equals(res)) {
                System.out.println("注册成功！请登录~");
            } else {
                System.out.println("注册失败：用户名已存在！");
            }

        } catch (Exception e) {
            System.out.println("注册异常：" + e.getMessage());
        }
    }
}