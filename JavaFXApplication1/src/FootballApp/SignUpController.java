/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FootballApp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author jasonsutanto
 */
public class SignUpController implements Initializable {
    @FXML
    private TextField tf_username;

    @FXML
    private TextField pf_password;

    @FXML
    private TextField tf_email;

    @FXML
    void logIn(MouseEvent event) throws IOException {
        //change scenes from sign up to log in 
        Parent root = FXMLLoader.load(getClass().getResource("logIn.fxml"));
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setScene(new Scene(root));    
    }

    @FXML
    void signUp(MouseEvent event) throws SQLException {
        
        Connection connection = DbConnect.getInstance().getConnection();
        
        try {
            String username = tf_username.getText();
            String email = tf_email.getText();
            String password = pf_password.getText();
            String sql = "insert into Users (username,password,email) values(?,?,?) ";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            int status = pstmt.executeUpdate();
            
            if(status > 0){
                System.out.println("user registered");
            }
            
        } catch (SQLException ex) {
            
            ex.printStackTrace();
        }
        
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
