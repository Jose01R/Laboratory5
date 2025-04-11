package controller;

import domain.CircularLinkedList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

public class AddEmployeeController
{
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TextField titleTextField;
    @javafx.fxml.FXML
    private TextField lastNameTextField;
    @javafx.fxml.FXML
    private DatePicker birthdayTextField;
    @javafx.fxml.FXML
    private TextField employeeIDTextField;
    @javafx.fxml.FXML
    private TextField firstNameTextField;

    //defino la lista enlazada interna
    private CircularLinkedList employeeList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.employeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Add Employee");
    }

    @Deprecated //pa que era eso?
    public void onKeyTypeAgeValidation(Event event) {
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        employeeIDTextField.clear();
        lastNameTextField.clear();
        firstNameTextField.clear();
        titleTextField.clear();
        birthdayTextField.setValue(null);
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "employee.fxml", bp);
    }
}