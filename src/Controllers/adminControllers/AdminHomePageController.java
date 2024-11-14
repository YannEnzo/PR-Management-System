/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.adminControllers;

import DAO.Database;
import static DAO.Database.loadData;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author yanne
 */
public class AdminHomePageController implements Initializable {

    @FXML
    private AnchorPane homeAnchorPane;
    @FXML
    private Label numberOfnodeslabel;
    

    @FXML
    private Label numberOfAvailaiblenodeslabel;

    public void setNumberOfnodes(Label numberOfnodeslabel) {
        this.numberOfnodeslabel = numberOfnodeslabel;
    }

    public void setNumberOfAvailaiblenodes(Label numberOfnodeslabel) {
        this.numberOfnodeslabel = numberOfnodeslabel;
    }
    
    

    public AnchorPane getHomeAnchorPane() {
        return homeAnchorPane;
    }

    public void setHomeAnchorPane(AnchorPane homeAnchorPane) {
        this.homeAnchorPane = homeAnchorPane;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
               loadData();
        numberOfnodeslabel.setText(String.valueOf(Database.numberOfnodes) );
        numberOfAvailaiblenodeslabel.setText(String.valueOf(Database.findAvailableNode().size()));
    }    
    
}
