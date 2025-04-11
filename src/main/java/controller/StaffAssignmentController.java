package controller;

import domain.CircularDoublyLinkedList;
import domain.ListException;
import domain.Staffing;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class StaffAssignmentController {
    @javafx.fxml.FXML
    private TableView<Staffing> staffAssignmentTableView;
    @javafx.fxml.FXML
    private TableColumn<Staffing, Integer> idTableColumn;//revisar que tipo es
    @javafx.fxml.FXML
    private TableColumn<Staffing, String> employeeIdTableColumn;//revisar que tipo
    @javafx.fxml.FXML
    private TableColumn<Staffing, String> employeeNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Staffing, String> jobPositionTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Staffing, String> assignationTypeTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Staffing, String> supervisorNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Staffing, String> dateTableColumn;
    @javafx.fxml.FXML
    private BorderPane bp;

    //defino la lista enlazada interna
    private CircularDoublyLinkedList staffAssignmentList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.staffAssignmentList = util.Utility.getStaffAssignmentList();
        alert = util.FXUtility.alert("Staff Assignment List", "Display Staff Assignment");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        employeeIdTableColumn.setCellValueFactory(new PropertyValueFactory<>("Employee Id"));
        employeeNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Employee name"));
        jobPositionTableColumn.setCellValueFactory(new PropertyValueFactory<>("Job Position"));
        assignationTypeTableColumn.setCellValueFactory(new PropertyValueFactory<>("Assignment Type"));
        supervisorNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Supervisor Name"));
        dateTableColumn.setCellValueFactory(new PropertyValueFactory<>("Date"));
        try{
            if(staffAssignmentList!=null && !staffAssignmentList.isEmpty()){
                for(int i=1; i<=staffAssignmentList.size(); i++) {
                    staffAssignmentTableView.getItems().add((Staffing) staffAssignmentList.getNode(i).data);
                }
            }
            //this.studentTableView.setItems(observableList);
        }catch(ListException ex){
            alert.setContentText("Staff Assignment list is empty");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.staffAssignmentList.clear();
        util.Utility.setStaffAssignmentList(this.staffAssignmentList); //actualizo la lista general
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
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addJStaffAssignment.fxml", bp);
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortEmplIdOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortAssigTypeOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortEmplNameOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void sortJobPositionOnAction(ActionEvent actionEvent) {
    }

    private void updateTableView() throws ListException {
        this.staffAssignmentTableView.getItems().clear(); //clear table
        this.staffAssignmentList = util.Utility.getStaffAssignmentList(); //cargo la lista
        if(staffAssignmentList!=null && !staffAssignmentList.isEmpty()){
            for(int i=1; i<=staffAssignmentList.size(); i++) {
                this.staffAssignmentTableView.getItems().add((Staffing) staffAssignmentList.getNode(i).data);
            }
        }
    }
}
