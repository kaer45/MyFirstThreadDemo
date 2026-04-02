package ui;

import chatapp.client.Client;
import ui.base.BaseUI;

public class HomeUI extends BaseUI {

    public void start() {
        while (true) {
            title("欢迎来到alij的聊天室");
            System.out.println("\t\t1 登录");
            System.out.println("\t\t2 注册");
            System.out.println("\t\t0 退出");
            split();

            System.out.print("请选择：");
            String choose = sc.nextLine();
            switch (choose) {
                case "1":
                    new LoginUI().show();
                    break;
                case "2":
                    new RegisterUI().show();
                    break;
                case "0":
                    System.out.println("感谢使用，再见！");
                    System.exit(0);
                default:
                    System.out.println("输入错误，请重新选择！");
            }
        }
    }
}