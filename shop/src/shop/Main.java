package shop;
import shop.Panel.MainPanel;

import java.util.Scanner;
import java.sql.*;
public class Main
{
    public static Scanner sc=new Scanner(System.in);
    public static void main(String[] args) throws SQLException {
        MainPanel mainPanel =new MainPanel();
        mainPanel.showMainMenu();
        //Admin ad = new Admin();
        //ad.login();
    }
}

