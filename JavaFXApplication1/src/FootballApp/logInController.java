/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FootballApp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author jasonsutanto
 */
public class logInController implements Initializable {
    
    @FXML
    private TextField tf_username;

    @FXML
    private TextField pf_password;

    @FXML
    void logIn(MouseEvent event) throws SQLException, IOException {
       String username = tf_username.getText();
       String password = pf_password.getText();
       String checkPassSql = "select * from Users where username = '"+username+"' and password = '"+password+"'";

       Connection connection = DbConnect.getInstance().getConnection();
       
       Statement statement = connection.createStatement();
       ResultSet rs = statement.executeQuery(checkPassSql);
       
       if(rs.next()){
        //change the next line from signUp scene to home scene  
        
        Parent root = FXMLLoader.load(getClass().getResource("home.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root)); 
       }  
    }

    @FXML
    void signUp(MouseEvent event) throws IOException {
        // when signUp button is clicked switch to signUp screen
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root)); 
    }
   
//    final public static void printResultSet(ResultSet rs) throws SQLException
//    {
//    ResultSetMetaData rsmd = rs.getMetaData();
//    int columnsNumber = rsmd.getColumnCount();
//     while (rs.next()) {
//        for (int i = 1; i <= columnsNumber; i++) {
//            if (i > 1) System.out.print(" | ");
//            System.out.print(rs.getString(i));
//        }
//        System.out.println("");
//     }
//    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
