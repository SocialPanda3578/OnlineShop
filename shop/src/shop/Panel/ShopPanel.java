package shop.Panel;

import shop.Main;
import shop.Shop;
import shop.User;

import java.sql.SQLException;
import shop.Cart;

public class ShopPanel extends MainPanel{
    Shop sto = new Shop();
    Cart crt = new Cart();
    CartPanel crtp = new CartPanel();
    public void SP_menu1(User user) throws SQLException, InterruptedException {
        sto.PrintItems();
        while (true) {
            System.out.println("***************************");
            System.out.println("\t1.购买商品");
            System.out.println("\t2.加入购物车");
            System.out.println("\t3.查看购物车");
            System.out.println("\t4.按商品编号进行排序");
            System.out.println("\t5.按商品价格进行排序");
            System.out.println("\t6.退出商城");
            System.out.println("***************************");
            System.out.print("请选择菜单:");
            int x = Main.sc.nextInt();
            if (x == 1) {
                sto.buy(user);
                Thread.sleep(1000 * 2);
                cls();
                sto.PrintItems();
            }
            else if (x == 2) {
                crt.addCart(user);
                Thread.sleep(1000 * 2);
                cls();
                sto.PrintItems();
            }
            else if (x == 3) {
                cls();
                crtp.cartPanel(user);
                sto.PrintItems();
            }
            else if (x == 4) {
                cls();
                sto.ID_ASC_Order();
            }
            else if (x == 5) {
                cls();
                sto.Price_ASC_Order();
            }
            else if (x == 6) {
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
