package controller;

import domain.CircularDoublyLinkedList;
import domain.JobPosition;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class JobPositionsController {
    @javafx.fxml.FXML
    private TableView<JobPosition> jobPositionsTableView;
    @javafx.fxml.FXML
    private TableColumn<JobPosition, Integer> idTableColumn;
    @javafx.fxml.FXML
    private TableColumn<JobPosition, String> hourlyWageTableColumn;
    @javafx.fxml.FXML
    private TableColumn<JobPosition, String> descriptionTableColumn;
    @javafx.fxml.FXML
    private TableColumn<JobPosition, String> totalHoursTableColumn;
    @javafx.fxml.FXML
    private TableColumn<JobPosition, String> monthlyWageTableColumn;
    @javafx.fxml.FXML
    private BorderPane bp;

    //defino la lista enlazada interna
    private CircularDoublyLinkedList jobPositionsList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.jobPositionsList = util.Utility.getJobPositionsList();
        alert = util.FXUtility.alert("Job Positions List", "Display Job Positions");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        descriptionTableColumn.setCellValueFactory(new PropertyValueFactory<>("Description"));
        hourlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("Hourly Wage"));
        totalHoursTableColumn.setCellValueFactory(new PropertyValueFactory<>("Total Hours"));
        monthlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("Monthly Wage"));
        try{
            if(jobPositionsList!=null && !jobPositionsList.isEmpty()){
                for(int i=1; i<=jobPositionsList.size(); i++) {
                    jobPositionsTableView.getItems().add((JobPosition) jobPositionsList.getNode(i).data);
                }
            }
            //this.studentTableView.setItems(observableList);
        }catch(ListException ex){
            alert.setContentText("Job Positions list is empty");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.jobPositionsList.clear();
        util.Utility.setJobPositionsList(this.jobPositionsList); //actualizo la lista general
        this.alert.setContentText("The list was deleted");
        this.alert.setAlertType(Alert.AlertType.INFORMATION);
        this.alert.showAndWait();
        try {
            updateTableView(); //actualiza el contenido del tableview
        } catch (ListException e) {
            throw new RuntimeException(e);
        }
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        //metodo de remove
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addJobPositions.fxml", bp);
    }

    @javafx.fxml.FXML
    public void sortByHourlyOnAction(ActionEvent actionEvent) {
        //metodo de sort por horas
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        //metodo de eliminar el ultimo
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        //metodo del prev
    }

    @javafx.fxml.FXML
    public void containsOnAction(ActionEvent actionEvent) {
        //metodo contains
    }

    @javafx.fxml.FXML
    public void sortByNameOnAction(ActionEvent actionEvent) {
        //metodo de contains
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        //metodo de next
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        //metodo de size
    }

    private void updateTableView() throws ListException {
        this.jobPositionsTableView.getItems().clear(); //clear table
        this.jobPositionsList = util.Utility.getJobPositionsList(); //cargo la lista
        if(jobPositionsList!=null && !jobPositionsList.isEmpty()){
            for(int i=1; i<=jobPositionsList.size(); i++) {
                this.jobPositionsTableView.getItems().add((JobPosition) jobPositionsList.getNode(i).data);
            }
        }
    }
}
