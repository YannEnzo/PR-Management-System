/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.CustomHashTable;
import DAO.Database;
import static DAO.Database.loadData;
import static DAO.Database.loadRequestFromDB;
import static DAO.Database.tenants;
import DAO.RBTree;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import model.Node;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import login.Main;
import model.Tenant;

/**
 * FXML Controller class
 *
 * @author jmato
 */
public class UserHomeController implements Initializable {

    @FXML
    private AnchorPane content1;

    @FXML
    private JFXTextField userName;
    
    @FXML
    private  JFXTextField currentDate;

    @FXML
    private TableView<Node> tableOfNodes;
    
    @FXML
    private TableColumn<Node, String> idCol;

    @FXML
    private TableColumn<Node, String> typeCol;

    @FXML
    private TableColumn<Node, String> categoryCol;

    @FXML
    private TableColumn<Node, Integer> rentCol;

    @FXML
    private TableColumn<Node, String> availabilityCol;

    @FXML
    private TableColumn<Node, String> tenantCol;

    
    @FXML
    private JFXTextField searchItem;

    public TableView<Node> getTableOfNodes() {
        return tableOfNodes;
    }

    public void setTableOfNodes(TableView<Node> tableOfNodes) {
        this.tableOfNodes = tableOfNodes;
    }

    @FXML
    void onCatalogue(ActionEvent event) {
        try {
            Locale.setDefault(Locale.ENGLISH);
            Parent root = FXMLLoader.load(getClass().getResource("/View/gallery.fxml"));
            Scene scene = new Scene(root);
            
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(UserHomeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
    }
    private void populateTableFromHashTable(CustomHashTable<String, Node> hashTable) {
    tableOfNodes.getItems().clear();
    hashTable.forEachValue(node -> tableOfNodes.getItems().add(node));
}
    @FXML
    void search(KeyEvent event) {
    String key = searchItem.getText();
    CustomHashTable<String, Node> filteredNodes = Database.searchNodes2(key);

    populateTableFromHashTable(filteredNodes);
}
    @FXML
    void onHome(ActionEvent event) {

    }
    @FXML
    void onDate(ActionEvent event) {
        setDate();
    }
     private void setDate() {
        LocalDate now = LocalDate.now();
        currentDate.setText("Today: "+now.toString());
    }
    
    @FXML

void onLogOut(ActionEvent event) throws IOException {
    Stage stage = (Stage) content1.getScene().getWindow();
    stage.close();

    Locale.setDefault(Locale.ENGLISH);
    Parent root = FXMLLoader.load(getClass().getResource("/View/login.fxml"));
    Scene scene = new Scene(root);
    
    Stage primaryStage = new Stage();
    primaryStage.setScene(scene);
    
    primaryStage.show();
}

@FXML
void onRequest(ActionEvent event) throws IOException {
   Node tn = tableOfNodes.getSelectionModel().getSelectedItem();
        if (tn == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Select an element from the table");
            a.setContentText("Nothing selected! Please select a node from the table before sending a Request");
            a.showAndWait();
        }else if(tn.getAvailability().equalsIgnoreCase("Occupied")){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Select an availaible node from the table");
            a.setContentText("This Node already is occupied! Please select a node from the table that is availaible to send a Request");
            a.showAndWait();
        }else{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/requestView.fxml"));
        Parent root = loader.load();
        RequestViewController requestController = loader.getController();
        requestController.setSelectedNode(tn);
    Locale.setDefault(Locale.ENGLISH);
    // Show the request view stage
        Stage requestStage = new Stage();
        requestStage.initModality(Modality.WINDOW_MODAL);
        requestStage.initOwner(content1.getScene().getWindow());
        requestStage.setScene(new Scene(root));
        requestStage.getIcons().add(new Image(Main.class.getResourceAsStream("/Resources/Images/logo.jpg")));
        requestStage.setResizable(false);
        requestStage.show();
}
}


    /**
     * Initializes the controller class.
     */

   @Override
public void initialize(URL url, ResourceBundle rb) {
    loadData();
    populateTableFromHashTable(Database.nodes);

    userName.setText("Mr " + UserloginController.uname.toUpperCase());
    setDate();
    
    idCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
    typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
    rentCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    availabilityCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
    categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    
    tenantCol.setCellValueFactory(cellData -> {
        Node node = cellData.getValue();
        if (node.getAvailability().equalsIgnoreCase("occupied")) {
            Tenant tenant = findTenantByNodeID(node.getRoomNumber());
            if (tenant != null) {
                return new SimpleStringProperty(tenant.getName());
            }
        }
        return new SimpleStringProperty("--");
    });

    tenantCol.setCellFactory(column -> new TableCell<Node, String>() {
        @Override
        protected void updateItem(String tenantName, boolean empty) {
            super.updateItem(tenantName, empty);
            if (empty || tenantName == null) {
                setText(null);
            } else {
                setText(tenantName);
            }
        }
    });
}

private Tenant findTenantByNodeID(String nodeID) {
    for (RBTree<CustomHashTable.MyEntry<String, Tenant>> tree : Database.tenants.table.values()) {
        for (CustomHashTable.MyEntry<String, Tenant> entry : tree) {
            Tenant tenant = entry.getValue();
            if (tenant.getNodeID().equals(nodeID)) {
                return tenant;
            }
        }
    }
    return null;
}


}
