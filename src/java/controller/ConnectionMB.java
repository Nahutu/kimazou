/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nahum
 */
public class ConnectionMB {
    private static String url = "jdbc:postgresql://localhost:5433/kimazoudb";
    private static String driver = "org.postgresql.Driver";
    private static String username = "kimUser";
    private static String password = "P@55w0rd!";
    private static Connection con;
    
    public static Connection getConnection(){
        try{
            Class.forName(driver);
            try{
                con = DriverManager.getConnection(url, username, password);
            }catch(SQLException ex){
                System.out.println("Failed to create the database connection.");
            }
        }catch(ClassNotFoundException ex){
            System.out.println("Driver not found." + ex.getMessage() );
        }
        return con;
    }
    
}
