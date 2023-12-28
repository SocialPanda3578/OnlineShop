package shop;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Shop {

    public void PrintItems()
    {
        System.out.println("商品编号\t名称\t\t价格\t\t数量");
        DBUtil db=new DBUtil();
        Object objs[]={};
        ResultSet rs=db.select("select * from items",objs);
        try {
            while (rs.next()) {
                String id = rs.getString("it_id");
                String na = rs.getString("it_name");
                Double price = rs.getDouble("it_price");
                Integer num = rs.getInt("it_nums");
                System.out.println(String.format("%-16s",id)+String.format("%-16s",na)+String.format("%-16s", price)+String.format("%-16s", num));
            }
        }
        catch(SQLException e1){
            e1.printStackTrace();
        }
        finally {
            db.closeConnection();
        }
    }

    public void ID_ASC_Order(){
        DBUtil db=new DBUtil();
        Object objs[]={};
        ResultSet rs=db.select("select * from items order by it_id asc",objs);
        try {
            while (rs.next()) {
                String id = rs.getString("it_id");
                String na = rs.getString("it_name");
                Double price = rs.getDouble("it_price");
                Integer num = rs.getInt("it_nums");
                System.out.println(String.format("%-16s",id)+String.format("%-16s",na)+String.format("%-16s", price)+String.format("%-16s", num));
            }
        }
        catch(SQLException e1){
            e1.printStackTrace();
        }
        finally {
            db.closeConnection();
        }
    }

    public void Price_ASC_Order(){
        DBUtil db=new DBUtil();
        Object objs[]={};
        ResultSet rs=db.select("select * from items order by it_price asc",objs);
        try {
            while (rs.next()) {
                String id = rs.getString("it_id");
                String na = rs.getString("it_name");
                Double price = rs.getDouble("it_price");
                Integer num = rs.getInt("it_nums");
                System.out.println(String.format("%-16s",id)+String.format("%-16s",na)+String.format("%-16s", price)+String.format("%-16s", num));
            }
        }
        catch(SQLException e1){
            e1.printStackTrace();
        }
        finally {
            db.closeConnection();
        }
    }

    public Items Find(String s) {
        DBUtil db = new DBUtil();
        Object obj[] = { s };
        ResultSet rs = db.select("select * from items where ca_id=? or ca_name=?", obj);
        try {
            while (rs.next()) {
                String id = rs.getString("it_id");
                String na = rs.getString("it_name");
                Double price = rs.getDouble("it_price");
                Integer num = rs.getInt("it_nums");
                System.out.println(id + ' ' + na + ' ' + price + ' ' + num);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        } finally {
            db.closeConnection();
        }
        Items i = new Items(null, null, null, null);
        return i;
    }

    public void buy(User user) throws SQLException {
        if (user.isLogin()) {
            System.out.println("想买点什么");
            String name = Main.sc.next();
            System.out.println("想买多少");
            int n = Main.sc.nextInt();
            
            Object obj[] = { name, name };
            DBUtil db = new DBUtil();
            ResultSet rs=db.select("select * from items where it_name=? or it_id=?", obj);
            if (rs.next()) {
                if (rs.getInt("it_nums") < n) {
                    System.out.println("库存不够");
                }
                else {
                    String id = rs.getString("it_id");
                    String na = rs.getString("it_name");
                    Double price = rs.getDouble("it_price");
                    Integer num = rs.getInt("it_nums");
                    db.update("insert into " + user.getUsername() + "_history values(?,?,?,?)", new Object[]{id,na,price,n});
                    db.update("update items set it_nums=it_nums-? where it_id=?", new Object[] { n, id });
                    db.update("delete from items where it_nums<=0", new Object[] {});
                    System.out.println("商品" + na + "购买成功");
                }
            }
            else {
                System.out.println("商品不存在");
            }
            db.closeConnection();
        }        
    }
}
