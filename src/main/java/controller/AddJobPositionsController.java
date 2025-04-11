package controller;

import domain.CircularDoublyLinkedList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

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
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
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
