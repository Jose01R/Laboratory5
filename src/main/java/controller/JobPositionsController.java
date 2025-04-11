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
    private TableColumn<JobPosition, Double> hourlyWageTableColumn;
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
        hourlyWageTableColumn.setCellValueFactory(new PropertyValueFactory<>("HourlyWage"));

        totalHoursTableColumn.setCellValueFactory(cellData -> {
            double hoursWorked = getTotalHours();
            return new javafx.beans.property.SimpleStringProperty(String.valueOf(hoursWorked));
        });

        monthlyWageTableColumn.setCellValueFactory(cellData -> {
            double hoursWorked = getTotalHours();
            //LLAMADA A METODO CALCULO SALARIO
            double salary = cellData.getValue().getSalary(hoursWorked);
            return new javafx.beans.property.SimpleStringProperty(String.format("%.2f", salary));
        });


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

        try {
            this.jobPositionsList.sort();
            util.Utility.setJobPositionsList(this.jobPositionsList);
            alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.setContentText("ORDERED LIST");
            alert.showAndWait();
            updateTableView();
        } catch (ListException e) {
            alert.setHeaderText("Error : " + e.getMessage());
            alert.show();
        }
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        //metodo de eliminar el ultimo
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        //metodo del prev
        try {
            JobPosition selected = jobPositionsTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                alert.setContentText("Please select an employee first.");
                alert.setAlertType(Alert.AlertType.WARNING);
            } else {
                Object prev = this.jobPositionsList.getPrev(selected);
                alert.setContentText("The previous element is: " + prev.toString());
                alert.setAlertType(Alert.AlertType.INFORMATION);
            }
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
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
        try {
            JobPosition selected = jobPositionsTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                alert.setContentText("Please select an employee first.");
                alert.setAlertType(Alert.AlertType.WARNING);
            } else {
                Object next = this.jobPositionsList.getNext(selected);
                alert.setContentText("The next element is: " + next.toString());
                alert.setAlertType(Alert.AlertType.INFORMATION);
            }
            alert.showAndWait();
        } catch (ListException e) {
            alert.setContentText("Error: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        //metodo de size
        try {
            this.alert.setContentText("The number of positions are : " + this.jobPositionsList.size());
            util.Utility.setJobPositionsList(this.jobPositionsList); //actualizo la lista general
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();
            updateTableView();//actualiza el contenido del tableview
            //disableButtonsIfListEmpty();
        } catch (ListException e) {
            alert.setHeaderText("Error: " + e.getMessage());
            alert.show();
        }
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

    private double getTotalHours(){
        return util.Utility.randomMinMax(40, 50);
    }
}
