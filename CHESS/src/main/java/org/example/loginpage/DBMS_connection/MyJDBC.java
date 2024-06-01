package org.example.loginpage.DBMS_connection;

import java.sql.*;

public class MyJDBC {
    public static final String DB_URL ="jdbc:mysql://127.0.0.1:3306/chesslogin";
    public static final String DB_username= "root";
    public static String DB_password= "Tarequl@0112310338#";
    public static String DB_user_table_name= "user";
    public static boolean register(String username, String password) {
        if(!CheckUser(username)){
            try {
                Connection connection = DriverManager.getConnection(DB_URL,
                        DB_username, DB_password);

                //create insert query
                PreparedStatement InsertUser = connection.prepareStatement(
                        "INSERT INTO "+ DB_user_table_name+ "(username, password)"+"VALUES(?, ?)"
                );
                InsertUser.setString(1, username);
                InsertUser.setString(2, password);

                InsertUser.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean CheckUser(String username) {
        try {
            Connection connection = DriverManager.getConnection(DB_URL,
                    DB_username, DB_password);

            PreparedStatement checkUserExists = connection.prepareStatement(
                    "SELECT * FROM " + DB_user_table_name + " WHERE USERNAME =?");
            checkUserExists.setString(1, username);

            ResultSet resultSet = checkUserExists.executeQuery();
            if (!resultSet.isBeforeFirst()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


    public static boolean login(String username, String password){
        try {
            Connection connection = DriverManager.getConnection(DB_URL,
                    DB_username, DB_password);

            // create select query
            PreparedStatement ValidUser = connection.prepareStatement(
                    "SELECT * FROM " + DB_user_table_name + " WHERE USERNAME = ? AND PASSWORD = ?"
                           );

            ValidUser.setString(1, username);
            ValidUser.setString(2, password);

            ResultSet resultSet =ValidUser.executeQuery();
            if(!resultSet.isBeforeFirst()){
                return false;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return true;
    }

}
