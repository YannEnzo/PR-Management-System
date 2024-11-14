/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.adminControllers;

import DAO.Database;
import static DAO.Database.loadData;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import login.Main;

/**
 * FXML Controller class
 *
 * @author yanne
 */
public class AdminHomeController implements Initializable {

   @FXML
    private JFXTextField userName;

   
    @FXML
    private AnchorPane content;

    public AnchorPane getContent() {
        return content;
    }

    public void setContent(AnchorPane content) {
        this.content = content;
    }

    @FXML
    void onHome(ActionEvent event) {
            try {
           FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/adminViews/adminHomePage.fxml"));
           AnchorPane pane = loader.load();
           content.getChildren().clear();
           content.getChildren().addAll(pane.getChildren());
           } catch (IOException ex) {
           Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
       }
           /*
           Parent root = FXMLLoader.load(getClass().getResource("/View/adminViews/adminHomePage.fxml"));
           
           Scene scene = new Scene(root);
           Stage st = new Stage();
           st.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
           st.setResizable(false);
           st.setScene(scene);
           st.show();
       } catch (IOException ex) {
           Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
       }*/
    
    }
    @FXML
    void onNodeMgnt(ActionEvent event) {
       try {
           FXMLLoader nodeMgntLoader = new FXMLLoader(getClass().getResource("/View/adminViews/nodeMngt.fxml"));
           AnchorPane nodePane = nodeMgntLoader.load();
           NodeMngtController controller = nodeMgntLoader.getController();
           controller.getNodeTable().getItems().clear();
       Database.nodes.forEachValue(node -> controller.getNodeTable().getItems().add(node));
           content.getChildren().clear();
           content.getChildren().addAll(nodePane);
       } catch (IOException ex) {
           Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
      @FXML
    void onLogOut(ActionEvent event) {
       try {
           Node source = (Node) event.getSource();
           Stage stage = (Stage) source.getScene().getWindow();
           stage.close();
           Locale.setDefault(Locale.ENGLISH);
           Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
           
           Scene scene = new Scene(root);
           Stage st = new Stage();
           st.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
           st.setResizable(false);
           st.setScene(scene);
           st.show();
       } catch (IOException ex) {
           Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }

    @FXML
    void onRequests(ActionEvent event) {
         try {
           FXMLLoader requestMgntLoader = new FXMLLoader(getClass().getResource("/View/adminViews/requestMgntView.fxml"));
           AnchorPane nodePane = requestMgntLoader.load();
           RequestMgntViewController controller = requestMgntLoader.getController();
           controller.getRequestTable().getItems().clear();
            Database.requests.forEachValue(request -> controller.getRequestTable().getItems().add(request));
           content.getChildren().clear();
           content.getChildren().addAll(nodePane);
       } catch (IOException ex) {
           Logger.getLogger(AdminHomeController.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        userName.setText("Mr "+AdminloginController.uname.toUpperCase());
    }    
    
}
