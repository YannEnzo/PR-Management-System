/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers.adminControllers;

import DAO.CustomHashTable;
import DAO.Database;
import static DAO.Database.loadData;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import model.Request;
import model.Tenant;

/**
 * FXML Controller class
 *
 * @author yanne
 */
public class RequestMgntViewController implements Initializable {

    @FXML
    private TableView<Request> requestTable;

    @FXML
    private TableColumn<Request, Integer> requestIDCol;

    @FXML
    private TableColumn<Request,String > roomNumberCol;

    @FXML
    private TableColumn<Request, String> nameCol;

    @FXML
    private TableColumn<Request, LocalDate> dobCol;

    @FXML
    private TableColumn<Request, String> phoneNumberCol;

    @FXML
    private TableColumn<Request, String> emailCol;

    @FXML
    private TableColumn<Request, LocalDateTime> timeOfRequestCol;

    @FXML
    private TableColumn<Request, String> descriptionCol;

    @FXML
    private JFXTextField searchItems;

    public TableView<Request> getRequestTable() {
        return requestTable;
    }

    public void setRequestTable(TableView<Request> requestTable) {
        this.requestTable = requestTable;
    }

    
    @FXML
    void onAccept(ActionEvent event) {
        Request selectedRequest = requestTable.getSelectionModel().getSelectedItem();
    if (selectedRequest == null) {
        // No request selected, show an error message or dialog
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("No Request Selected");
        alert.setContentText("Please select a request to accept.");
        alert.showAndWait();
    } else {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Confirmation");
    confirmation.setHeaderText("Accept Request");
    confirmation.setContentText("Are you sure you want to accept this request?");

    ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);

    confirmation.getButtonTypes().setAll(buttonYes, buttonNo);

    Optional<ButtonType> result = confirmation.showAndWait();
    if (result.isPresent() && result.get() == buttonYes) {
        int requestId = selectedRequest.getRequestId();
                Tenant newTenant = new Tenant(
                selectedRequest.getName(),
                selectedRequest.getGender(),
                java.sql.Date.valueOf(selectedRequest.getDateOfBirth()),
                selectedRequest.getPhoneNumber(),
                selectedRequest.getEmail(),
                new java.sql.Date(System.currentTimeMillis()), // Set the current date as dateOfEntry
                selectedRequest.getRoomNumber()
            );
            
            // Add the new tenant to the database (assuming you have a method for that)
            Database.createTenant(newTenant);
            // Update the availability of the node to "Available"
            String roomNumber = selectedRequest.getRoomNumber();
            Database.changeNodeAvailability(roomNumber, "Occupied");
            
            // Delete the accepted request
            Database.deleteRequest(requestId);

            // Update the UI
            requestTable.getItems().remove(selectedRequest);
            
            // Display a success message or perform any other UI updates
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Request Accepted");
            alert.setHeaderText("Request has been accepted. He will be contacted to be allocated his new property soon.");
            alert.showAndWait();
    }
    }
    }

    @FXML
void onRevoke(ActionEvent event) {
    Request selectedRequest = requestTable.getSelectionModel().getSelectedItem();

    if (selectedRequest == null) {
        // No request selected, display an error message
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("Please select a request to revoke.");
        alert.showAndWait();
        return;
    }

    Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
    confirmation.setTitle("Confirmation");
    confirmation.setHeaderText("Revoke Request");
    confirmation.setContentText("Are you sure you want to revoke this request?");

    ButtonType buttonYes = new ButtonType("Yes", ButtonBar.ButtonData.YES);
    ButtonType buttonNo = new ButtonType("No", ButtonBar.ButtonData.NO);

    confirmation.getButtonTypes().setAll(buttonYes, buttonNo);

    Optional<ButtonType> result = confirmation.showAndWait();
    if (result.isPresent() && result.get() == buttonYes) {
        // Delete the request object from the database (assuming you have a deleteRequestFromDB method)
        Database.deleteRequest(selectedRequest.getRequestId());

        // Refresh the request table to reflect the changes
        requestTable.getItems().remove(selectedRequest);

        // Show a success message
        Alert success = new Alert(Alert.AlertType.INFORMATION);
        success.setTitle("Success");
        success.setHeaderText(null);
        success.setContentText("Request revoked successfully.");
        success.showAndWait();
    }
}


   @FXML
void search(KeyEvent event) {
    String key = searchItems.getText();
    CustomHashTable<Integer, Request> filteredRequests = Database.searchRequest(key);

    requestTable.getItems().clear();
    filteredRequests.forEachValue(request -> requestTable.getItems().add(request));
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
            loadData();
        requestIDCol.setCellValueFactory(new PropertyValueFactory<>("requestId"));
        roomNumberCol.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("requestDescription"));
        timeOfRequestCol.setCellValueFactory(new PropertyValueFactory<>("timeOfRequest"));
    timeOfRequestCol.setCellFactory(column -> {
        return new TableCell<Request, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText("");
                } else {
                    // Customize the date format as you need, for example:
                    setText(item.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
                }
            }
        };
    });
        populateTableFromHashTable(Database.requests);
    }  
    private void populateTableFromHashTable(CustomHashTable<Integer, Request> hashTable) {
    requestTable.getItems().clear();
    hashTable.forEachValue(request -> requestTable.getItems().add(request));
}
    
}
