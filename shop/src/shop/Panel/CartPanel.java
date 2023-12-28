package shop.Panel;

import java.sql.SQLException;

import shop.Main;
import shop.User;
import shop.Cart;

public class CartPanel extends MainPanel{
    public void cartPanel(User user) throws SQLException {
        Cart cart = new Cart();
        if (user.isLogin()) {
            cart.refreshCart(user);
            cart.printCart(user);
            while (true) {
                System.out.println("***************************");
                System.out.println("\t1.删除购物车内商品");
                System.out.println("\t2.购物车结算");
                System.out.println("\t3.清空购物车");
                System.out.println("\t4.返回上一页");
                System.out.println("***************************");
                System.out.print("请选择菜单:");
                int x = Main.sc.nextInt();
                if (x == 1) {
                    cls();
                    cart.removeCart(user);
                } 
                else if (x == 2) {
                    cls();
                    cart.checkOut(user);
                } 
                else if (x == 3) {
                    cls();
                    cart.removeAll(user);
                }
                else if (x == 4) {
                    cls();       
                    break;
                } 
                else {
                    cls();
                    System.out.println("无效输入！");
                }
            }
        } else {
            System.out.println("请登录后使用");
        }
    }
    
}
