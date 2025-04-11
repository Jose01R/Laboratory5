package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class EmployeeController
{
    @javafx.fxml.FXML
    private BorderPane bp;
    @javafx.fxml.FXML
    private TableView<Employee> employeeTableView;
    @javafx.fxml.FXML
    private TableColumn<Employee, Integer> idTableColumn;//este tambien
    @javafx.fxml.FXML
    private TableColumn<Employee, String> titleTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> firstNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> lastNameTableColumn;
    @javafx.fxml.FXML
    private TableColumn<Employee, String> birtdayTableColumn;//este tengo que ver
    //defino la lista enlazada interna
    private CircularLinkedList employeeList;
    private Alert alert; //para el manejo de alertas

    @javafx.fxml.FXML
    public void initialize() {
        //cargamos la lista general
        this.employeeList = util.Utility.getEmployeeList();
        alert = util.FXUtility.alert("Employee List", "Display Employee");
        alert.setAlertType(Alert.AlertType.ERROR);
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("Id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("Last Name"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("First Name"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("Title"));
        birtdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("Birtday"));
        try{
            if(employeeList!=null && !employeeList.isEmpty()){
                for(int i=1; i<=employeeList.size(); i++) {
                    employeeTableView.getItems().add((Employee) employeeList.getNode(i).data);
                }
            }
            //this.studentTableView.setItems(observableList);
        }catch(ListException ex){
            alert.setContentText("Employee list is empty");
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void clearOnAction(ActionEvent actionEvent) {
        this.employeeList.clear();
        util.Utility.setEmployeeList(this.employeeList); //actualizo la lista general
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
    public void containsOnAction(ActionEvent actionEvent) {
        //metodo contains
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        //metodo size
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addEmployee.fxml", bp);
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        //metodo de remove
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        //metodo de removeFirst
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        //metodo de prev
    }

    @javafx.fxml.FXML
    public void sortByIdOnAction(ActionEvent actionEvent) {
        //metodo de sort ID
    }

    @javafx.fxml.FXML
    public void sortByNameOnAction(ActionEvent actionEvent) {
        //metodo de sort name
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        //sort name
    }

    private void updateTableView() throws ListException {
        this.employeeTableView.getItems().clear(); //clear table
        this.employeeList = util.Utility.getEmployeeList(); //cargo la lista
        if(employeeList!=null && !employeeList.isEmpty()){
            for(int i=1; i<=employeeList.size(); i++) {
                this.employeeTableView.getItems().add((Employee) employeeList.getNode(i).data);
            }
        }
    }

}