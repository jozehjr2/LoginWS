/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author Junior
 */
public class DBConnection {
    //INFO DATABASE
    private static final String database = "jdbc:postgresql://localhost:5432/Usuarios"; //Address of database = localhost
    private static final String driver = "org.postgresql.Driver"; //Driver
    private static final String db_user = "postgres"; //User database
    private static final String db_pass = "admin";  //Password database
    
    private static Connection con = null;
    
    //METHOD TO RETURN THE CONNECTION WITH DATABASE
    public static Connection getDBConnection(){
        //Testing if the object con was not started
        if (con == null){
            try {
                Class.forName(driver); //Indicating the driver
                con = DriverManager.getConnection(database, db_user, db_pass); //Creating the connection with database
            } catch (ClassNotFoundException ex) {
                System.out.println("The driver was not found!!");
            } catch (SQLException ex) {
                System.out.println("Error to connect to database: "+ex.getMessage());
            }
        }
        return con;
    }
    
    //METHOD THAT RECEIVES SQL COMMANDS
    public static PreparedStatement getPreparedStatement(String sql){
        //Testing if the connection was already created
        if (con == null){
            con = getDBConnection(); //Create connection
        }
        try {
            return con.prepareStatement(sql); //Return a object java.sql.PreparedStatement
        } catch (SQLException e){
            System.out.println("Error in SQL: "+e.getMessage());
        }
        return null;
    }
    
    
}
