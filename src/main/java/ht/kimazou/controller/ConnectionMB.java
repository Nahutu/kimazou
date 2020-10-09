/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ht.kimazou.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author nahum
 */
public class ConnectionMB {
    private static String url = "jdbc:postgresql://ec2-50-17-197-184.compute-1.amazonaws.com:5432/d9v8qoorvsbar9";
    private static String driver = "org.postgresql.Driver";
    private static String username = "waixybglglfeqa";
    private static String password = "8896b7295b55b66e2987436440524d554da18a3fc9314ce3096b7840c25e8c71";
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
