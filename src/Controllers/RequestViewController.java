/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import DAO.Database;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import model.Node;
import model.Request;

/**
 * FXML Controller class
 *
 * @author jmato
 */
public class RequestViewController implements Initializable {

     @FXML
    private JFXTextField name;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField phoneNumber;

    @FXML
    private JFXDatePicker date;

    @FXML
    private JFXRadioButton maleGender;
    
    @FXML
    private JFXRadioButton femaleGender;

     @FXML
    private ToggleGroup genders;
     @FXML
    private TextArea description;
    private Node selectedNode; // variable to store the selected Node object

    public void setSelectedNode(Node node) {
        selectedNode = node;
       
    }
    @FXML
    void onReset(ActionEvent event) {
        name.setText("");
        date.setValue(null);
        phoneNumber.setText("");
        email.setText("");
        description.setText("");
        maleGender.setSelected(false);
        femaleGender.setSelected(false);
    }

    @FXML
    void onSubmitRequest(ActionEvent event) {
         
        // Retrieve the user input from the form
    String nameValue = name.getText();
    String emailValue = email.getText();
    String phoneNumberValue = phoneNumber.getText();
    LocalDate dateOfBirthValue = date.getValue();
    String genderValue = maleGender.isSelected() ? "Male" : (femaleGender.isSelected() ? "Female" : "");
    LocalDateTime timeOfRequest = LocalDateTime.now(); // Use the current timestamp as the time of the request
    String requestDescription = description.getText(); // Get the request description from the text area
    String roomNumberValue = selectedNode.getRoomNumber();
    // Validate the required fields
    if (nameValue.isEmpty() || emailValue.isEmpty() || phoneNumberValue.isEmpty() || dateOfBirthValue == null || genderValue.isEmpty()) {
       Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Missing Information");
        alert.setContentText("Please fill in all required fields.");
        alert.showAndWait();
        return; // Exit the method    
    }
    if(requestDescription.isEmpty()){
        requestDescription = "No Description";
    }
// Create a new Request object
    Request request = new Request(nameValue, genderValue, dateOfBirthValue, phoneNumberValue, emailValue, timeOfRequest, roomNumberValue, requestDescription);
        Database.createRequest(request);
        
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText("Request Created");
    alert.setContentText("Your request has been submitted successfully.");
    alert.showAndWait();
    
    javafx.scene.Node source = (javafx.scene.Node) event.getSource();
           Stage stage = (Stage) source.getScene().getWindow();
           stage.close();
    
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
