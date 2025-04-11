package controller;

import domain.CircularDoublyLinkedList;
import domain.Employee;
import domain.JobPosition;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

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
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Remove Job Position");
        inputDialog.setHeaderText("Enter the ID of the Job Position to remove:");
        inputDialog.setContentText("ID:");

        Optional<String> result = inputDialog.showAndWait();
        //detecta si se cerró el diálogo sin escribir nada, o presionó Cancelar
        if (!result.isPresent()) return;

        String input = result.get().trim();
        int idToRemove;

        try {
            idToRemove = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            alert.setContentText("Invalid ID format. Please enter a valid number.");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return;
        }

        try {
            JobPosition toRemove = null;

            for (int i = 1; i <= jobPositionsList.size(); i++) {
                JobPosition jobPosition = (JobPosition) jobPositionsList.getNode(i).data;
                if (util.Utility.compare(jobPosition.getId(), idToRemove)==0) {
                    toRemove = jobPosition;
                    break;
                }
            }

            if (toRemove == null) {
                alert.setContentText("No Job Position found with ID: " + idToRemove);
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.showAndWait();
                return;
            }

            final JobPosition finalToRemove = toRemove;

            //alerta que confirma que si quiero eliminar al puesto de trabajo con ese id
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete this job position?");
            confirmAlert.setContentText("ID: " + finalToRemove.getId() +
                    "\nDescription: " + finalToRemove.getDescription());
            //es el boton que elimina al puesto de trabajo con el id
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        jobPositionsList.remove(finalToRemove);
                        util.Utility.setJobPositionsList(jobPositionsList);
                        alert.setContentText("Job Position with ID " + finalToRemove.getId() + " was successfully removed.");
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.showAndWait();
                        updateTableView();
                    } catch (ListException e) {
                        alert.setContentText("Error removing job position: " + e.getMessage());
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.showAndWait();
                    }
                }
            });

        } catch (ListException e) {
            alert.setContentText("Error accessing job positions list: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
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
            this.alert.setContentText("ORDERED LIST");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
            updateTableView();
        } catch (ListException e) {
            alert.setHeaderText("Error : " + e.getMessage());
            alert.show();
        }
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {

        try {
            Object elementRemoved=this.jobPositionsList.removeLast();
            util.Utility.setJobPositionsList(this.jobPositionsList);//Actualizar lista
            this.alert.setContentText("Last element removed : " +elementRemoved);
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();
            updateTableView();//Actualizar tableView
        }catch (ListException e){
            alert.setHeaderText("Error: "+e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }

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
        try {
            this.jobPositionsList.sortByDescription();//Metodo de CircularDoublyLinkedList que ordena por descripcion
            util.Utility.setJobPositionsList(this.jobPositionsList);//Actualiaza la lista
            alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.setContentText("ORDERED LIST");
            alert.showAndWait();
            updateTableView();//Actualiza el tableView
        } catch (ListException e) {
            alert.setHeaderText("Error : " + e.getMessage());
            alert.show();
        }
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
