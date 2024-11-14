/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.adminControllers;

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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import login.Main;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class AdminloginController implements Initializable {

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
                    
                    PreparedStatement pat = con.prepareStatement("select * from admins where username=? and pass=?");
                    pat.setString(1, uname);
                    pat.setString(2, pass);
                    
                    ResultSet rs= pat.executeQuery();
                    
                    if(rs.next()){
                        
                     Node source = (Node) event.getSource();
                    Stage stage = (Stage) source.getScene().getWindow();
                    stage.close();
                    Database.loadData();
                     Locale.setDefault(Locale.ENGLISH);
                    Stage st = new Stage();
                    FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/View/adminViews/adminHome.fxml"));
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/adminViews/adminHomePage.fxml"));
                    AnchorPane pane = loader.load();

                    AnchorPane homePane = homeLoader.load();

                    AdminHomeController controller = homeLoader.getController();

                    controller.getContent().getChildren().clear();

                    controller.getContent().getChildren().addAll(pane.getChildren());

                    Scene scene = new Scene(homePane);   
                       st.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
                       st.setResizable(false);
                       st.setScene(scene);
                       st.show();
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
                    Logger.getLogger(AdminloginController.class.getName()).log(Level.SEVERE, null, ex);
                }
       
        }
            }



    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
