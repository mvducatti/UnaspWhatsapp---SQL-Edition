package com.example.marcos.unaspwhatsapp_sqledition.Database;

import android.os.StrictMode;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;


public class DB{

    private static Connection conn;

    private static Connection conecta() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        StrictMode.ThreadPolicy po = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(po);
        Class.forName("org.postgresql.Driver").newInstance();
        String url = "jdbc:postgresql://10.0.2.2/unaspht";
        String user = "postgres";
        String pass = "roketp0wer";
        conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }
    private static void desconecta() throws SQLException {
        if (conn != null){
            try{
                conn.close();
            }catch (Exception e){
                throw e;
            }finally {
                conn = null;
            }
        }
    }

    public static ResultSet select(String query) throws SQLException, ClassNotFoundException, InterruptedException, ExecutionException, InstantiationException, IllegalAccessException{
        ResultSet resultSet = null;
        resultSet = conecta().createStatement().executeQuery(query);
        return resultSet;
    }

    public static ResultSet execute(String query) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException{
        ResultSet resultSet = null;
        resultSet = conecta().prepareStatement(query).executeQuery();
        return resultSet;
    }

    public static int executeQuery(String query) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        return conecta().createStatement().executeUpdate(query);
    }

    public static void update(String query) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        conecta().createStatement().executeUpdate(query);
    }
}

