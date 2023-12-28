package shop.Panel;

import shop.Admin;
import shop.Main;
import shop.Shop;

import java.sql.SQLException;

public class AdminPanel extends MainPanel{
    Admin admin = new Admin();
    public void FirstMenu() throws SQLException {
        while (true) {
            System.out.println("***************************");
            System.out.println("\t1.管理商城");
            System.out.println("\t2.查看用户信息");
            System.out.println("\t3.退出管理员登录");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int x = Main.sc.nextInt();
            if (x == 1) {
                cls();
                ManageItems();
            } 
            else if (x == 2) {
                cls();
                ManageUsers();
            } 
            else if (x == 3)
            {
                cls();
                admin.exit();
                break;
            } 
            else {
                cls();
                System.out.println("无效输入！");
            }
        }
    }

    public  void ManageUsers() throws SQLException {
        while (true)
        {
            admin.printUsers();
            System.out.println("***************************");  
            System.out.println("\t1.修改用户信息");
            System.out.println("\t2.添加用户信息");
            System.out.println("\t3.删除用户信息");
            System.out.println("\t4.返回上一步");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int n = Main.sc.nextInt();
            if (n == 1) {
                cls();
                admin.updateUser();
            } else if (n == 2) {
                cls();
                admin.addUser();
            } else if (n == 3) {
                cls();
                admin.deleteUser();
            } else if (n == 4) {
                cls();
                break;
            } else {
                cls();
                System.out.println("无效输入");
            }
        }
    }

    public void ManageItems() throws SQLException{
            Shop sto=new Shop();
            while (true) {
            sto.PrintItems();
            System.out.println("***************************");
            System.out.println("\t1.修改商品信息");
            System.out.println("\t2.添加商品信息");
            System.out.println("\t3.删除商品信息");
            System.out.println("\t4.返回上一步");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int x= Main.sc.nextInt();
            if (x == 1) {
                cls();
                admin.updateItems();
            } 
            else if (x == 2) {
                cls();
                admin.addItems();
            } 
            else if (x == 3) {
                cls();
                admin.deleteItems();
            } 
            else if (x == 4) {
                cls();
                break;
            } 
            else {
                cls();
                System.out.println("无效输入");
            }
        }
    }
}