package shop.Panel;

import shop.Admin;
import shop.History;
import shop.Main;
import shop.Shop;
import shop.User;

import java.io.IOException;
import java.sql.SQLException;

public class MainPanel {
    public void showMainMenu() throws SQLException, InterruptedException {
        System.out.println(" _      _                                  ");
        System.out.println(" |  |  /         /                         ");
        System.out.println(" | /| /    __   /    __    __   _  _    __ ");
        System.out.println(" |/ |/   /___) /   /   ' /   ) / /  ) /___)");
        System.out.println("_/__|___(___ _/___(___ _(___/_/_/__/_(___ _");
        boolean flag = true;
        while (flag) {
            System.out.println("***************************");
            System.out.println("\t1.注册");
            System.out.println("\t2.登录");
            System.out.println("\t3.退出登录");
            System.out.println("\t4.查看商城");
            System.out.println("\t5.查看我购买的商品");
            System.out.println("\t6.管理员登录");
            System.out.println("\t7.退出系统");
            System.out.println("***************************");
            System.out.print("请选择菜单:");

            int choice = Main.sc.nextInt();
            Admin admin = new Admin();
            User user = new User();
            switch (choice) {
                case 1:
                    cls();
                    System.out.println("注册");
                    user.reg();
                    break;
                case 2:
                    cls();
                    System.out.println("登录");
                    user.login();
                    break;
                case 3:
                    cls();
                    user.exit();
                    break;
                case 4:
                    cls();
                    System.out.println("查看商城");
                    ShopPanel sp = new ShopPanel();
                    sp.SP_menu1(user);
                    break;
                case 5:
                    cls();
                    System.out.println("查看我购买的商品");
                    History history = new History();
                    history.PrintHistoryItems(user);
                    break;
                case 6:
                    cls();
                    System.out.println("管理员登录");
                    admin.login();
                    break;
                case 7:
                    cls();
                    System.out.println("系统退出");
                    flag = false;
                    break;
                default:
                    cls();
                    System.out.println("输入错误请重新输入！");
            }
        }
    }

    public void cls() {
        int n = 50;
        while (n != 0) {
            System.out.println("");
            n--;
        }
    }
}
