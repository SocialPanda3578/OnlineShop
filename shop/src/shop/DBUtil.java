package shop;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBUtil {
    //连接信息
    private static String driverName;
    private static String url;
    private static String username;
    private static String password;

    //注册驱动，使用静态块，只需注册一次
    static {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader("C:\\Users\\2022\\Desktop\\专业实训\\shop\\src\\db.properties"));
            driverName = properties.getProperty("driverName");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //jdbc对象
    private Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;

    //获取连接
    public void getConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //更新操作：增删改
    public int update(String sql, Object[] objs) {
        int i = 0;
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            if(objs!=null) for (int j = 0; j < objs.length; j++) {
                preparedStatement.setObject(j + 1, objs[j]);
            }
            i = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return i;
    }

    public ResultSet select(String sql, Object[] objs) {
        try {
            getConnection();
            preparedStatement = connection.prepareStatement(sql);
            for (int j = 0; j < objs.length; j++) {
                preparedStatement.setObject(j + 1, objs[j]);
            }
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void closeConnection() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}