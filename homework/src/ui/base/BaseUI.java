package ui.base;

import java.util.Scanner;

public class BaseUI {
    protected Scanner sc = new Scanner(System.in);

    // 分割线
    protected void split() {
        System.out.println("======================================");
    }

    // 标题
    protected void title(String str) {
        split();
        System.out.println("\t\t" + str);
        split();
    }
}