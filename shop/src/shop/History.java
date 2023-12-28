package shop;

import java.sql.ResultSet;
import java.sql.SQLException;

public class History {
    public void PrintHistoryItems(User user) {
        if (user.isLogin()) {
            DBUtil db = new DBUtil();
            Object obj[] = {};
            String username = user.getUsername();
            ResultSet rs = db.select("select * from " + username + "_history", obj);
            try {
                while (rs.next()) {
                    String id = rs.getString("id");
                    String na = rs.getString("name");
                    Double price = rs.getDouble("price");
                    Integer num = rs.getInt("nums");
                    System.out.println(id + "\t\t" + na + "\t\t\t" + price + "\t\t\t" + num);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                db.closeConnection();
            }
        } else {
            System.out.println("用户未登录");
        }
    }

    public void addHistory(User user,Object obj[]/*id name price nums*/) {
        DBUtil db = new DBUtil();
            db.update("insert into " + user.getUsername() + "_history", obj);
    }
}
