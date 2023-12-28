package shop.Panel;

import shop.Admin;
import shop.Main;
import shop.Shop;

import java.sql.SQLException;

public class AdminPanel {
    Admin admin = new Admin();
    public void FirstMenu() throws SQLException {
        while (true) { //一级菜单
            System.out.println("\t1.管理商城");
            System.out.println("\t2.查看用户信息");
            System.out.println("\t3.退出登录");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int x = Main.sc.nextInt();
            if (x == 1)
                ManageItems();
            else if (x == 2)
                ManageUsers();
            else if (x == 3)
                break;
            else
                System.out.println("无效输入！");
        }
    }

    public  void ManageUsers() throws SQLException {
        while (true)
        {
            admin.printUsers();
            System.out.println("\t1.修改用户信息");
            System.out.println("\t2.添加用户信息");
            System.out.println("\t3.删除用户信息");
            System.out.println("\t4.返回上一步");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int n = Main.sc.nextInt();
            if (n == 1) admin.updateUser();
            else if (n == 2) admin.addUser();
            else if (n == 3) admin.deleteUser();
            else if (n == 4) break;
            else System.out.println("无效输入");
        }
    }

    public void ManageItems() throws SQLException{
            Shop sto=new Shop();
            while (true) {
            sto.PrintItems();
            System.out.println("\t1.修改商品信息");
            System.out.println("\t2.添加商品信息");
            System.out.println("\t3.删除商品信息");
            System.out.println("\t4.返回上一步");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int x= Main.sc.nextInt();
            if(x==1) admin.updateItems();
            else if(x==2) admin.addItems();
            else if(x==3) admin.deleteItems();
            else if(x==4) break;
            else System.out.println("无效输入");
        }
    }
}