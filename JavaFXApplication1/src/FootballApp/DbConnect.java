/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FootballApp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author jasonsutanto
 */
public class DbConnect {
    
    private DbConnect(){
    
    }
    
    public static DbConnect getInstance(){
        
       return new DbConnect();
    }
    
    public Connection getConnection() throws SQLException{
        
        // make sure to change url for the correct path
       String url = "jdbc:sqlite:C:\\Users\\jojmo\\football.db";
	Connection conn = DriverManager.getConnection(url);
	return conn;
    }
    
}
