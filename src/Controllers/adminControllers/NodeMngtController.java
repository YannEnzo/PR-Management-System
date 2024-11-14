/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.adminControllers;

import DAO.CustomHashTable;
import DAO.Database;
import static DAO.Database.loadData;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.Node;
import model.Tenant;

/**
 * FXML Controller class
 *
 * @author yanne
 */
public class NodeMngtController implements Initializable {

      @FXML
    private TableView<Node> nodeTable;

    @FXML
    private TableColumn<Node, String> roomNumberCol;

    @FXML
    private TableColumn<Node, String> roomTypeCol;

    @FXML
    private TableColumn<Node, Integer> priceCol;

    @FXML
    private TableColumn<Node,String > statusCol;

    @FXML
    private TableColumn<Node, String> locationCol;

    @FXML
    private TableColumn<Node, String> categoryCol;

    @FXML
    private TextField searchItems;

    public TableView<Node> getNodeTable() {
        return nodeTable;
    }

    public void setNodeTable(TableView<Node> nodeTable) {
        this.nodeTable = nodeTable;
    }

    @FXML
void onFreeNode(ActionEvent event) {
    // Check if a node is selected
    Node selectedNode = nodeTable.getSelectionModel().getSelectedItem();
    if (selectedNode == null) {
        showAlert(Alert.AlertType.ERROR, "Error", "No Node Selected", "Please select a node to free.");
        return;
    }
    
    // Check if the node is occupied
    if (selectedNode.getAvailability().equals("Occupied")) {
        String nodeID = selectedNode.getRoomNumber();
        
        // Find the associated tenant
        Tenant associatedTenant = Database.findTenantByNodeID(nodeID);
        if (associatedTenant == null) {
            showAlert(Alert.AlertType.ERROR, "Error", "No Associated Tenant", "No tenant is associated with the selected node.");
            return;
        }
        
        // Show a confirmation message with the tenant's name
        String tenantName = associatedTenant.getName();
        boolean confirmed = showConfirmationDialog("Confirmation", "Free Node", "Are you sure you want to free this node?\n\n It is occupied by: Mr/Ms " + tenantName.toUpperCase());
        
        if (confirmed) {
            // Delete the associated tenant from the database
            Database.deleteTenant(associatedTenant.getTenantID());
            
            // Update the node's availability
            Database.changeNodeAvailability(selectedNode.getRoomNumber(), "Available");
            
            nodeTable.refresh();
            
            // Show a success message or perform any necessary action
            showAlert(Alert.AlertType.INFORMATION, "Success", "Node Freed", "The node has been freed successfully.");
        }
    } else {
        // Node is not occupied, show an error message or perform any necessary action
        showAlert(Alert.AlertType.ERROR, "Error", "Node Not Occupied", "The selected node is not occupied.");
    }
}

private void showAlert(Alert.AlertType alertType, String title, String headerText, String contentText) {
    Alert alert = new Alert(alertType);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);
    alert.showAndWait();
}

private boolean showConfirmationDialog(String title, String headerText, String contentText) {
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText(headerText);
    alert.setContentText(contentText);

    ButtonType buttonTypeYes = new ButtonType("Yes");
    ButtonType buttonTypeNo = new ButtonType("No");

    alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

    Optional<ButtonType> result = alert.showAndWait();
    return result.isPresent() && result.get() == buttonTypeYes;
}

    @FXML
    void onAdd(ActionEvent event) {
          try {
              FXMLLoader addNodeLoader = new FXMLLoader(getClass().getResource("/View/adminViews/addAndEditNodes.fxml"));
              AnchorPane nodePane = addNodeLoader.load();
              addAndEditNodesController controller = addNodeLoader.getController();
              controller.setNodeTable(nodeTable);
              controller.initializeFields();
              Stage stage = new Stage();
               Scene scene = new Scene(nodePane);
                stage.setScene(scene);
                stage.show();
                refreshTable();
          } catch (IOException ex) {
              Logger.getLogger(NodeMngtController.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
@FXML
void onDelete(ActionEvent event) {
    Node nt = nodeTable.getSelectionModel().getSelectedItem();
    if (nt == null) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setHeaderText("Select a Node");
        a.setContentText("No Node selected! Please select a node from the table before clicking on the 'Delete' button.");
        a.showAndWait();
    } else {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirmation");
        confirmAlert.setHeaderText("Delete Node");
        confirmAlert.setContentText("Are you sure you want to delete this node?");

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        confirmAlert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == buttonTypeYes) {
            // User clicked Yes, proceed with deletion
            Database.deleteNode(nt.getRoomNumber());
            refreshTable();
            // Remove the node from the table
            nodeTable.getItems().remove(nt);

            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Node deleted");
            confirmAlert.setContentText("Node deleted Successfully!");
        }
    }
}





    @FXML
    void onEdit(ActionEvent event) {
             Node nt = nodeTable.getSelectionModel().getSelectedItem();
        if (nt == null) {
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setHeaderText("Select a Node");
            a.setContentText("No Node selected! Please select a node from the table before clicking on the button 'edit'");
            a.showAndWait();
        }else{
                 try {
                     FXMLLoader addNodeLoader = new FXMLLoader(getClass().getResource("/View/adminViews/addAndEditNodes.fxml"));
                     AnchorPane nodePane = addNodeLoader.load();
                     addAndEditNodesController controller = addNodeLoader.getController();
               
                     controller.setNodeTable(nodeTable);
                     controller.initializeFields();
                     controller.getRoomNumber().setText(nt.getRoomNumber());
                     controller.getRoomType().setText(nt.getType());
                     controller.getLocation().setText(nt.getLocation());
                     controller.getPrice().setText(String.valueOf(nt.getPrice()));
                     controller.getAvailability().setValue(nt.getAvailability());
                     controller.getCategory().setValue(nt.getCategory());
                     controller.setNode(nt);
                     controller.setFlag(true);
                     Stage stage = new Stage();
                     Scene scene = new Scene(nodePane);
                     stage.setScene(scene);
                     stage.show();
                    refreshTable();
                 } catch (IOException ex) {
                     Logger.getLogger(NodeMngtController.class.getName()).log(Level.SEVERE, null, ex);
                 }
        }
    }
     @FXML
    void search(KeyEvent event) {
        String key = searchItems.getText();
        CustomHashTable<String, Node> filteredNodes = Database.searchNodes2(key);

    nodeTable.getItems().clear();
    filteredNodes.forEachValue(node -> nodeTable.getItems().add(node));
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadData();
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        roomTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("availability"));
        locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        categoryCol.setCellValueFactory(new PropertyValueFactory<>("category"));
    populateTableFromHashTable(Database.nodes);    
    }
    private void populateTableFromHashTable(CustomHashTable<String, Node> hashTable) {
    nodeTable.getItems().clear();
    hashTable.forEachValue(node -> nodeTable.getItems().add(node));
}
    private void refreshTable() {
    nodeTable.getItems().clear();
    Database.nodes.forEachValue(node -> nodeTable.getItems().add(node));
}

    }    

    

