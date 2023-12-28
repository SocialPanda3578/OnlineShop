package shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import shop.Panel.*;

public class Admin extends User{
    public Admin(){

    }
    public Admin(String name,String pass){
        setUsername(name);
        setPassword(pass);
    }
    
    public void login(){
        if (isLogin()) {
            System.out.println(getUsername()+"已登录，不能重复登录！");
        } else {
            String username = "";
            String password = "";
            while (true) {
                System.out.print("请输入用户名：");
                username = Main.sc.next();
                System.out.print("请输入密码：");
                password = Main.sc.next();
                if (password == null || username == null) {
                    System.out.println("用户名或密码不能为空");
                    continue;
                }
                DBUtil db = new DBUtil();
                Object[] obj = { username, password };
                String sql = "select * from admins where ad_name=? and ad_password=?";
                ResultSet rs = db.select(sql, obj);
                try {
                    if (!rs.next()) {
                        System.out.println("用户名或密码错误！请重试！");
                        continue;
                    } else {
                        setUsername(username);
                        setPassword(password);
                        setStatus(true);
                        System.out.println("管理员" + username + " 登录成功！");
                        AdminPanel ap = new AdminPanel();
                        ap.FirstMenu();
                    }
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    db.closeConnection();
                    break;
                }
            }
        }
    }

    public void printUsers(){
        DBUtil db=new DBUtil();
        Object[] objs={};
        ResultSet set = db.select("select * from users", objs);
        System.out.println("用户名"+"\t\t密码");
        try {
            while (set.next()){
                String u_name=set.getString("u_username");
                String u_pass=set.getString("u_password");
                System.out.println(String.format("%-16s", u_name)+String.format("%-16s",u_pass));
            }
        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        finally {
            db.closeConnection();
        }
    }
    public void updateUser() throws SQLException {
        DBUtil db=new DBUtil();
        System.out.println("***************************");
        System.out.print("请输入需修改的用户名:");
        String targetUser = Main.sc.next();
        if(checkUser(targetUser)) {
            System.out.print("请输入新用户名：");
            String newName = Main.sc.next();
            System.out.print("请输入新的密码：");
            String newPassword = Main.sc.next();

            Object[] obj = {newName, newPassword, targetUser};
            int i=db.update("update users set u_username=?,u_password=? where u_username=?", obj);
            if(i!=0) System.out.println("用户修改成功");
            else System.out.println("用户修改失败");
        }
        else{
            System.out.println("用户不存在，请检查输入！");
        }
    }
    public void addUser() throws SQLException {
        System.out.print("请输入用户名:");
        String newName = Main.sc.next();
        DBUtil db=new DBUtil();
        if(checkUser(newName)) System.out.println("用户名已存在");
        else
        {
            System.out.print("请输入密码：");
            String newPassword=Main.sc.next();
            Object[] objs={newName,newPassword};
            int i=db.update("insert into users values(?,?)",objs);
            if(i==0) System.out.println("用户创建失败");
            else {
                System.out.println("用户创建成功");
                String sql = "CREATE TABLE " + newName + "_cart"
                        + " (id VARCHAR(30), name VARCHAR(40), price DOUBLE,nums INT)";
                db.update(sql, null);
                sql = "CREATE TABLE " + newName + "_history"
                        + " (id VARCHAR(30), name VARCHAR(40), price DOUBLE,nums INT)";
                db.update(sql, null);
            }
            db.closeConnection();
        }
    }
    public void deleteUser() throws SQLException {
        System.out.print("请输入用户名:");
        String newName = Main.sc.next();
        DBUtil db=new DBUtil();
        if(!checkUser(newName)) System.out.println("用户名不存在");
        else
        {
            Object[] obj={newName};
            int i=db.update("delete from users where u_username=?",obj);
            if(i==0) System.out.println("用户删除失败");
            else {
                db.update("drop table " + newName + "_cart", null);
                db.update("drop table " + newName + "_history", null);
                System.out.println("用户删除成功");
            }
            db.closeConnection();
        }
    }
    public void updateItems() throws SQLException {
        DBUtil db = new DBUtil();
        System.out.print("请输入需修改的商品编号:");
        String targetItems = Main.sc.next();
        if (checkItems(targetItems)) {
            System.out.print("请输入新的商品名称");
            String newName = Main.sc.next();
            System.out.print("请输入新的商品价格");
            String newPrice = Main.sc.next();
            System.out.print("请输入新的商品数量");
            String newNums = Main.sc.next();
            Object[] obj =   { newName, newPrice, newNums ,targetItems};
            int i = db.update("update items set it_name=?,it_price=?,it_nums=? where it_id=?", obj);
            if (i != 0)
                System.out.println("商品修改成功");
            else
                System.out.println("商品修改失败");
        }
        else {
            System.out.println("商品不存在，请检查输入！");
        }
    }
    public void addItems() {
        System.out.println("请依次输入 编号 名称 价格 数量");
        String id = Main.sc.next();
        String name = Main.sc.next();
        Double price = Main.sc.nextDouble();
        int nums = Main.sc.nextInt();
        DBUtil db = new DBUtil();
        Object obj[] = { id, name, price, nums };
        int i = db.update("insert into items values(?,?,?,?)", obj);
        if (i == 1)
            System.out.println("商品上架成功");
        else
            System.out.println("商品上架失败");
    }
    public void deleteItems() {
        System.out.println("输入 要删除商品的编号");
        String id = Main.sc.next();
        DBUtil db = new DBUtil();
        Object[] obj = { id };
        int i = db.update("delete from items where it_id=?", obj);
        if (i == 0)
            System.out.println("商品删除失败");
        else
            System.out.println("商品删除成功");
        db.closeConnection();
    }
    public boolean checkUser(String name) throws SQLException {
        DBUtil db = new DBUtil();
        Object[] obj = { name };
        ResultSet rs = db.select("select * from users where u_username=?", obj);
        if (rs.next())
            return true;
        else
            return false;
    }
    public boolean checkItems(String id) throws SQLException {
        DBUtil db = new DBUtil();
        Object[] obj = { id };
        ResultSet rs = db.select("select * from items where it_id=?", obj);
        if (rs.next()) {
            return true;
        }
        else {
            return false;
        }
    }
}