package shop;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Cart {
    public void cartPanel(User user) throws SQLException {
        if (user.isLogin()) {
            refreshCart(user);
            printCart(user);
            while (true) {
                System.out.println("\t1.删除购物车内商品");
                System.out.println("\t2.购物车结算");
                System.out.println("\t3.返回上一页");
                System.out.println("***************************");
                System.out.print("请选择菜单:");
                int x = Main.sc.nextInt();
                if (x == 1)
                    removeCart(user);
                else if (x == 2)
                    checkOut(user);
                else if (x == 3)
                    break;
                else
                    System.out.println("无效输入！");
            }
        } else {
            System.out.println("请登录后使用");
        }
    }

    public void refreshCart(User user) throws SQLException {
        if (user.isLogin()) {
            DBUtil db = new DBUtil();
            Object obj[] = {};
            ResultSet rs = db.select("select * from " + user.getUsername() + "_cart ", obj);
            while (rs.next()) {
                String id = rs.getString("id");
                ResultSet rs2 = db.select("select * from items where it_id=?", new Object[] { id });
                if (rs2.next()) {
                    Double price = rs2.getDouble("it_price");
                    db.update("update " + user.getUsername() + "_cart set price=? where id=?",
                            new Object[] { price, id });
                }
            }
        }
    }

    public void addCart(User user) throws SQLException {
        if (user.isLogin()) {
            System.out.println("想买点啥？");
            String str = Main.sc.next();
            System.out.println("想买多少？");
            int n = Main.sc.nextInt();
            DBUtil db = new DBUtil();
            Object obj[] = { str, str };
            int i = db.update("update " + user.getUsername() + "_cart set nums=nums+" + n + " where id=? or name=?",
                    obj);
            if (i == 0) {
                ResultSet rs = db.select("select * from items where it_name=? or it_id=?", obj);
                if (rs.next()) {
                    if (n > rs.getInt("it_nums")) {
                        System.out.println("库存不够");
                    } else {
                        Object obj2[] = { rs.getString("it_id"), rs.getString("it_name"), rs.getDouble("it_price"), n };
                        db.update("insert into " + user.getUsername() + "_cart value(?,?,?,?)", obj2);
                        System.out.println("加购物车成功");
                    }
                } else {
                    System.out.println("商品不存在！");
                }
            }
            db.closeConnection();
        } else {
            System.out.println("请登录后使用");
        }
    }

    public void removeCart(User user) {
        if (user.isLogin()) {
            System.out.print("请输入要删除的商品名称或编号");
            String s = Main.sc.next();
            System.out.print("要删除多少件对应商品");
            int n = Main.sc.nextInt();
            DBUtil db = new DBUtil();
            Object obj[] = { n, s, s };
            int i = db.update("update " + user.getUsername() + "_cart set nums=nums-? where id=? or name=?", obj);
            if (i == 0)
                System.out.println("物品删除失败");
            else {
                System.out.println("物品删除成功");
                db.closeConnection();
            }
            Object obj2[] = {};
            db.update("delete from " + user.getUsername() + "_cart where nums<0", obj2);
        } else {
            System.out.println("请登录后使用");
        }
    }

    public void printCart(User user) throws SQLException {
        if (user.isLogin()) {
            refreshCart(user);
            DBUtil db = new DBUtil();
            Object obj[] = {};
            ResultSet rs = db.select("select * from " + user.getUsername() + "_cart", obj);
            try {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    Double price = rs.getDouble("price");
                    int nums = rs.getInt("nums");
                    System.out.println(id + ' ' + name + ' ' + price + ' ' + nums);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                db.closeConnection();
            }
        }
        else {
            System.out.println("请登录后使用");
        }
    }

    public void checkOut(User user) throws SQLException {
        if (user.isLogin()) {
            refreshCart(user);
            DBUtil db = new DBUtil();
            Object obj[] = {};
            ResultSet rs = db.select("select * from " + user.getUsername() + "_cart ", obj);
            try {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String name = rs.getString("name");
                    Double price = rs.getDouble("price");
                    int nums = rs.getInt("nums");
                    ResultSet rs2 = db.select("select * from items where it_id=?", new Object[] { id });
                    if (rs2.next()) {
                        if (rs2.getInt("it_nums") < nums) {
                            nums = rs2.getInt("it_nums");
                            System.out.println("抱歉!" + name + "商品数量不足以满足您的需求");
                            System.out.println("已为您更改需求数量");
                        }
                    }
                    else {
                        System.out.println("抱歉!编号为"+id+"的"+ name + "商品不存在或已售空!");
                        continue;
                    }
                    Object obj2[] = { id, name, price, nums };
                    db.update("insert into " + user.getUsername() + "_history values(?,?,?,?)", obj2);//加入历史
                    db.update("update items set it_nums=it_nums-? where it_id=?", new Object[] { nums, id });//删除item
                    System.out.println("商品"+name+"购买成功!");
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                db.update("delete from items where it_nums<=0", new Object[] {});
                removeAll(user);
                db.closeConnection();
            }
        }
        else {
            System.out.println("请登录后操作");
        }
    }

    public void removeAll(User user) {
        if (user.isLogin()) {
            DBUtil db = new DBUtil();
            Object obj[] = {};
            int i = db.update("delete from " + user.getUsername() + "_Cart", obj);
            if (i != 0)
                System.out.println("清空购物车成功!");
            else
                System.out.println("清空购物车失败!");
        }
        else
            System.out.println("请登录后操作");
    }
}
