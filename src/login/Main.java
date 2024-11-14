/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author HP
 */
public class Main extends Application {
    
    private double x = 0;
    private double y = 0; 
    @Override
    public void start(Stage stage) throws Exception {
        
        Locale.setDefault(Locale.ENGLISH);
        Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
        
        Scene scene = new Scene(root);
        /*root.setOnMousePressed((MouseEvent event) ->{
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) ->{
            stage.setX(event.getScreenX()-x);
            stage.setY(event.getScreenY()-y);
           stage.setOpacity(.8);
        });
        
        root.setOnMouseDragged((MouseEvent event)->{
            stage.setOpacity(1);
        });
        
        stage.initStyle(StageStyle.TRANSPARENT);
        */
        stage.setScene(scene);
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
        stage.setResizable(false);
        stage.show();
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
