/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.adminControllers;

import DAO.CustomHashTable;
import DAO.Database;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import model.Node;

/**
 *
 * @author yanne
 */
public class addAndEditNodesController implements Initializable{
    
    @FXML
    private JFXTextField roomType;

    @FXML
    private JFXTextField location;

    @FXML
    private JFXTextField price;

    @FXML
    private JFXComboBox<Object> availability;
    
    @FXML
    private JFXComboBox<Object> category;


    @FXML
    private JFXTextField roomNumber;
    
    private boolean flag;

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
    private Node node;

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    
    @FXML
    private TableView<Node> nodeTable;

    public void setNodeTable(TableView<Node> nodeTable) {
        this.nodeTable = nodeTable;
    }

    public JFXTextField getRoomType() {
        return roomType;
    }

    public void setRoomType(JFXTextField roomType) {
        this.roomType = roomType;
    }

    public JFXTextField getLocation() {
        return location;
    }

    public void setLocation(JFXTextField location) {
        this.location = location;
    }

    public JFXTextField getPrice() {
        return price;
    }

    public void setPrice(JFXTextField price) {
        this.price = price;
    }

   /* public JFXComboBox<Node> getAvailability() {
        return availability;
    }

    public void setAvailability(JFXComboBox<Node> availability) {
        this.availability = availability;
    } 

    public JFXComboBox<Node> getCategory() {
        return category;
    }

    public void setCategory(JFXComboBox<Node> category) {
        this.category = category;
    }*/ 

    public JFXComboBox<Object> getAvailability() {
        return availability;
    }

    public void setAvailability(JFXComboBox<Object> availability) {
        this.availability = availability;
    }

    public JFXComboBox<Object> getCategory() {
        return category;
    }

    public void setCategory(JFXComboBox<Object> category) {
        this.category = category;
    }

    
    

    public JFXTextField getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(JFXTextField roomNumber) {
        this.roomNumber = roomNumber;
    }
    
    @FXML
    void onReset(ActionEvent event) {
        roomNumber.setText("");
        roomType.setText("");
        location.setText("");
        price.setText("");
       availability.getSelectionModel().clearSelection();
       availability.setValue(null);
        category.getSelectionModel().clearSelection();
        category.setValue(null);
    }

    @FXML
    void onSubmit(ActionEvent event) {
            Node n = new Node(roomNumber.getText(), roomType.getText(), Integer.parseInt(price.getText()), (availability.getValue()).toString(), location.getText(), (category.getValue()).toString());
            /*System.out.println(availability.toString());
            System.out.println(category.toString());*/
            if(!flag){
                Database.nodes.put(n.getRoomNumber(), n); // Assuming 'getRoomNumber' gives the key
        Database.saveNode(n);
                nodeTable.getItems().add(n);
                 refreshTable();
            }
            else{
                nodeTable.getItems().set(nodeTable.getItems().indexOf(node), n);
                 Database.nodes.put(n.getRoomNumber(), n); // Update in hash table
        Database.updateNode(roomNumber.getText(), n); // Update in database
        flag = false;
        refreshTable();
            }
            nodeTable.getItems().clear();
    Database.nodes.forEachValue(node -> nodeTable.getItems().add(node)); // Repopulate table

    Stage stage = (Stage)((javafx.scene.Node) event.getSource()).getScene().getWindow();
    stage.close();

    
    Alert a = new Alert(Alert.AlertType.INFORMATION);
    a.setContentText("A new Node was successfully added or updated.");
    a.showAndWait();
    populateTableFromHashTable(Database.nodes);
    }
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        } 
    public void initializeFields() {
    availability.getItems().addAll("Available", "Occupied");
    category.getItems().addAll("Standard", "Deluxe");
    if (flag) {
        roomNumber.setEditable(false);
        roomType.setEditable(false);
        location.setEditable(false);
    }
}
 private void populateTableFromHashTable(CustomHashTable<String, Node> hashTable) {
    nodeTable.getItems().clear();
    hashTable.forEachValue(node -> nodeTable.getItems().add(node));

}
private void refreshTable() {
    nodeTable.getItems().clear();
    Database.nodes.forEachValue(node -> nodeTable.getItems().add(node));
}}
 



