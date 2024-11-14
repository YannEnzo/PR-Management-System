/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Controllers.adminControllers.NodeMngtController;
import DAO.Database;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import login.Main;

/**
 *
 * @author HP
 */
public class LoginController implements Initializable {
    
    @FXML
    void adminlogin(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
         Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("/View/adminViews/adminlogin.fxml"));
        
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
        st.setResizable(false);
        st.setScene(scene);
        st.show();
    }

    @FXML
    void userlogin(ActionEvent event) throws IOException {
        Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            stage.close();
         Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("/View/userlogin.fxml"));
        Scene scene = new Scene(root);
        Stage st = new Stage();
        st.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
        st.setResizable(false);
        st.setScene(scene);
        st.show();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
