package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.css.converter.StringConverter;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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


    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        String title = titleTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String employeeIDText = employeeIDTextField.getText().trim();
        String firstName = firstNameTextField.getText().trim();
        LocalDate dateLocal = birthdayTextField.getValue();

        // Verifica si todos los campos están completos
        if (title.isEmpty() || lastName.isEmpty() || employeeIDText.isEmpty() || firstName.isEmpty() || dateLocal == null) {
            util.FXUtility.alert("ERROR", "Todos los campos deben ser completados.").showAndWait();
            return;
        }

        // Crear el nuevo registro

        // Convertimos LocalDate a Date
        LocalDateTime localDateTime = dateLocal.atStartOfDay();
        java.util.Date date = java.util.Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());

        int employeeID = Integer.parseInt(employeeIDText); //se convierte a int

        try {
            if (!employeeList.isEmpty()) {
                for (int i = 1; i <= employeeList.size(); i++) {
                    Employee existing = (Employee) employeeList.getNode(i).data;
                    if (existing.getId() == employeeID) {
                        util.FXUtility.alert("ERROR", "Ya existe un empleado con este ID.").showAndWait();
                        employeeIDTextField.clear();
                        return;
                    }
                }
            }
        } catch (Exception e) {
            util.FXUtility.alert("ERROR", "Error al validar ID: " + e.getMessage()).showAndWait();
            return;
        }

        Employee employee = new Employee(employeeID, lastName, firstName, title, date);

        employeeList.add(employee);
        util.FXUtility.alertInfo("Employee added", "The employee has been added").showAndWait();

        employeeIDTextField.clear();
        lastNameTextField.clear();
        firstNameTextField.clear();
        titleTextField.clear();
        birthdayTextField.setValue(null);

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