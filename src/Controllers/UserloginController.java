/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.adminControllers.AdminloginController;
import DAO.Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class UserloginController implements Initializable {

     @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    void onCancle(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onReset(ActionEvent event) {
            username.setText("");
            password.setText("");
    }
public static String uname;
    @FXML
    void onSubmit(ActionEvent event) {
         uname = username.getText();
            String pass = password.getText();
            
            if(uname.equals("")|| pass.equals("")){
                JOptionPane.showMessageDialog(null, "Username or password blank.");
            }
            else{
             
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","root","yannenzo");
                    
                    PreparedStatement pat = con.prepareStatement("select * from users where username=? and pass=?");
                    pat.setString(1, uname);
                    pat.setString(2, pass);
                    
                    ResultSet rs= pat.executeQuery();
                    if(rs.next()){
//                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
//                        a.setHeaderText("Login Successful");
//                        a.setContentText("Welcome Dear user!");
//                        a.showAndWait();
                        Node source = (Node) event.getSource();
                        Stage stage = (Stage) source.getScene().getWindow();
                        stage.close();
                        Database.loadData();
                        Locale.setDefault(Locale.ENGLISH);
                        Parent root = FXMLLoader.load(getClass().getResource("/View/userHome.fxml"));
                        //Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                        Scene scene = new Scene(root);
                        stage.setScene(scene);
                        stage.show();
                        
                    }
                    else{
                        Alert b = new Alert(Alert.AlertType.ERROR);
                        b.setHeaderText("Invalid credentials");
                        b.setContentText("The login and or the password is incorect");
                        b.showAndWait();
                        username.setText("");
                        password.setText("");
                        username.requestFocus();
                    }
                    
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                Logger.getLogger(UserloginController.class.getName()).log(Level.SEVERE, null, ex);
            }
       
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }      
    
}
