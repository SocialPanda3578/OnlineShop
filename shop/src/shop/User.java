package shop;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.Console;
import java.lang.constant.Constable;

public class User
{
    private static String user_name;
    private static String pass_word;
    private static boolean status=false;

    public String getUsername()
    {
        return user_name;
    }
    public String getPassword()
    {
        return  pass_word;
    }
    public void setPassword(String password)
    {
        pass_word = password;
    }
    public void  setUsername(String username){user_name=username;}
    public static boolean isLogin()
    {
        return status;
    }
    public static void setStatus(boolean status) {
        User.status = status;
    }

    public void reg() //用户注册
    {
        String username = "";
        String password = "";
        while (true) {
            System.out.print("请输入用户名：");
            username = Main.sc.next();
            boolean fg = true;

            DBUtil db = new DBUtil();
            Object[] objs = { username };
            String sql = "select * from users where u_username=?";
            ResultSet rs = db.select(sql, objs);
            try {
                if (rs.next()) {
                    System.out.println("用户名重复");
                    continue;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            } finally {
                db.closeConnection();
            }

            if (fg == false)
                continue;
            else if (username.length() < 3) {
                System.out.println("用户名长度至少大于3位！");
                continue;
            } else
                break;
        }
        while (true) {
            System.out.print("请输入登录密码：");
            password = Main.sc.next();
            if (password.length() < 6) {
                System.out.println("密码长度至少大于6位！");
                continue;
            }
            int digit = 0;
            int letter = 0;
            for (int i = 0; i < password.length(); i++) {
                if (Character.isDigit(password.charAt(i)))
                    digit++;
                if (Character.isLetter(password.charAt(i)))
                    letter++;
            }
            if (digit == 0 || letter == 0) {
                System.out.println("密码至少含有一个字符和一个数字！");
                continue;
            }
            System.out.print("请再次确认您的密码");
            Console console = System.console();
            char[] repassword = console.readPassword();
            //String repassword = Main.sc.next();
            if (password.equals(String.valueOf(repassword)))//插入用户数据
            {
                DBUtil db = new DBUtil();
                Object[] obj = { username, password };
                //ResultSet set=db.select("select * from users",obj);
                int i = db.update("insert into users values(?,?)", obj);
                if (i != 0) {
                    System.out.println("用户创建成功,已为您自动登录");
                    String sql = "CREATE TABLE " + username + "_cart"
                            + " (id VARCHAR(30), name VARCHAR(40), price DOUBLE,nums INT)";
                    db.update(sql, null);
                    sql = "CREATE TABLE " + username + "_history"
                            + " (id VARCHAR(30), name VARCHAR(40), price DOUBLE,nums INT)";
                    db.update(sql, null);
                    user_name = username;
                    pass_word = password;
                    status = true;
                    System.out.println("普通用户" + username + " 登录成功！");
                } else
                    System.out.println("用户创建失败");
                db.closeConnection();
                break;
            } else {
                System.out.println("两次输入的密码不一致！");
            }
        }
    }

    public void login()
    {
        if (status) {
            System.out.println(user_name+"已登录，不能重复登录！");
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
                String sql = "select * from users where u_username=? and u_password=?";
                ResultSet rs = db.select(sql, obj);
                try {
                    if (!rs.next()) {
                        System.out.println("用户名或密码错误！请重试！");
                        continue;
                    } else {
                        user_name = username;
                        pass_word = password;
                        status = true;
                        System.out.println("普通用户" + username + " 登录成功！");
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

    public void exit() {
        if (status) {
            status = false;
            System.out.println("再见" + user_name + "!");
        }
    }
}
