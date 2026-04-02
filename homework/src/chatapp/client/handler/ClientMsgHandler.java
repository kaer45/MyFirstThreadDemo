package chatapp.client.handler;

import chatapp.client.Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientMsgHandler implements Runnable {

    @Override
    public void run() {
        try {
            Socket socket = Client.getSocket();
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String msg;
            while ((msg = br.readLine()) != null) {
                // 关键：不覆盖“我：”输入框
                System.out.println("\n"+ msg);
                System.out.print("我：");
            }
        } catch (Exception e) {
            System.out.println("\n与服务器断开连接！");
        }
    }
}