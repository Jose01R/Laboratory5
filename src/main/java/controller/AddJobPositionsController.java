package controller;

import domain.CircularDoublyLinkedList;
import domain.Employee;
import domain.JobPosition;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class AddJobPositionsController {

    @javafx.fxml.FXML
    private TextField jobPositionIDTextField;//se va aumentado, inicia en 1
    @javafx.fxml.FXML
    private TextField hourlyWageTextField;
    @javafx.fxml.FXML
    private TextField descriptionTextField;
    @javafx.fxml.FXML
    private BorderPane bp;

    //defino la lista enlazada interna
    private CircularDoublyLinkedList jobPositionsList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.jobPositionsList = util.Utility.getJobPositionsList();
        alert = util.FXUtility.alert("Job Positions List", "Add Job Positions");

        // Mostrar el siguiente ID disponible
        int nextId = JobPosition.getAutoId() + 1;
        jobPositionIDTextField.setText(String.valueOf(nextId));
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        String hourlyWageText = hourlyWageTextField.getText().trim();
        String description = descriptionTextField.getText().trim();

        if (description.isEmpty() || hourlyWageText.isEmpty()) {
            util.FXUtility.alert("ERROR", "Todos los campos deben ser completados.").showAndWait();
            return;
        }

        try {
            double hourlyWage = Double.parseDouble(hourlyWageText);

            JobPosition jobPosition = new JobPosition(description, hourlyWage);

            jobPositionsList.add(jobPosition);
            util.Utility.setJobPositionsList(jobPositionsList); // actualizo lista global

            util.FXUtility.dialog("Job Position Added", "The job position has been added.");

            descriptionTextField.clear();
            hourlyWageTextField.clear();

            // Mostrar el nuevo ID disponible
            int nextId = JobPosition.getAutoId() + 1;
            jobPositionIDTextField.setText(String.valueOf(nextId));

        } catch (NumberFormatException e) {
            util.FXUtility.alert("ERROR", "El salario por hora debe ser un número válido.").showAndWait();
        } catch (Exception e) {
            util.FXUtility.alert("ERROR", "Ocurrió un error al agregar el puesto: " + e.getMessage()).showAndWait();
        }
    }


    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        jobPositionIDTextField.clear();
        descriptionTextField.clear();
        hourlyWageTextField.clear();
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "jobPositions.fxml", bp);
    }
}
