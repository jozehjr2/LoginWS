/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

/**
 *
 * @author Junior
 */
public class UserDAO {
    
    public UserDAO(){
        
    }
    
    //INSERT - CREATE USERS
    public boolean insert(User user){
        String sql = "INSERT INTO usuario(nome,login,email,senha,perfil) VALUES(?,?,?,?,?)";
        Boolean ret = false; //return if inserted = false
        PreparedStatement pst = DBConnection.getPreparedStatement(sql);
        
        try {
            pst.setString(1, user.getName());
            pst.setString(2, user.getLogin());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getType());
            
            if(pst.executeUpdate()>0){
                ret = true; //return true
            } 
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            ret = false; //return false
        }
        
        return ret;
    }
    
    //UPDATE USERS
    public boolean update(User user){
        String sql = "UPDATE usuario set nome=?,senha=?,perfil=?,email=? where login=?";
        Boolean ret = false; //return if updated = false
        PreparedStatement pst = DBConnection.getPreparedStatement(sql);
        
        try {
            pst.setString(1, user.getName());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getType());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getLogin());
            
            if(pst.executeUpdate()>0){
                ret = true; //return true
            }   
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            ret = false; //return false
        }
        
        return ret;
    }
    
    //DELETE USERS
    public boolean delete(User user){
        String sql = "DELETE FROM usuario where login=?";
        Boolean ret = false; //return if deleted = false
        PreparedStatement pst = DBConnection.getPreparedStatement(sql);
        
        try {
            pst.setString(1, user.getLogin());
            
            if(pst.executeUpdate()>0){
                ret = true; //return true
            }    
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            ret = false; //return false
        }
        
        return ret;
    }
    
    //LISTING USERS
    public List<User> listing(){
        String sql = "SELECT * FROM usuario";
        List<User> ret = new ArrayList<User>(); //return
        
        PreparedStatement pst = DBConnection.getPreparedStatement(sql);
        
        try {
            ResultSet res = pst.executeQuery();
            
            while(res.next()){
                User item = new User();
                item.setId(res.getInt("ID_user"));
                item.setName(res.getString("nome"));
                item.setLogin(res.getString("login"));
                item.setPassword(res.getString("senha"));
                item.setEmail(res.getString("email"));
                item.setType(res.getString("perfil"));
                
                ret.add(item);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return ret;
    }
    
    //SEARCHING USERS
    public User search(User user){
        String sql = "SELECT * FROM usuario where login=?";
        User ret = null; //return
        
        PreparedStatement pst = DBConnection.getPreparedStatement(sql);
        
        try {
            pst.setString(1, user.getLogin());
            ResultSet res = pst.executeQuery();
            
            if(res.next()){
                ret = new User();
                ret.setId(res.getInt("ID_user"));
                ret.setName(res.getString("nome"));
                ret.setLogin(res.getString("login"));
                ret.setPassword(res.getString("senha"));
                ret.setEmail(res.getString("email"));
                ret.setType(res.getString("perfil"));  
            }   
        } catch (SQLException ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex); 
        }
        
        return ret;
    }
    
}
