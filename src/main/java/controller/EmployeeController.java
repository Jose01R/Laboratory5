package controller;

import domain.CircularLinkedList;
import domain.Employee;
import domain.ListException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

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
        idTableColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        lastNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameTableColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        titleTableColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        birtdayTableColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
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
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Contains Employee");
        inputDialog.setHeaderText("Enter the ID of the employee you want to search for:");
        inputDialog.setContentText("ID:");

        Optional<String> result = inputDialog.showAndWait();
        //detecta si se cerró el diálogo sin escribir nada, o presionó Cancelar
        if (!result.isPresent()) return;

        String input = result.get().trim();
        int idToSearch;

        try {
            idToSearch = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            alert.setContentText("Invalid ID format. Please enter a valid number.");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
            return;
        }

        try {
            Employee toSearch = null;

            for (int i = 1; i <= employeeList.size(); i++) {
                Employee employee = (Employee) employeeList.getNode(i).data;
                if (util.Utility.compare(employee.getId(), idToSearch)==0) {
                    toSearch = employee;
                    break;
                }
            }

            if (toSearch == null) {
                alert.setContentText("Not employee found with ID: " + idToSearch);
                alert.setAlertType(Alert.AlertType.WARNING);
            } else {
                boolean exists = employeeList.contains(toSearch);
                alert.setContentText("Employee with ID " + toSearch.getId() + " was " +
                        (exists ? "it's on the list. \nLastName and FirstName: " + toSearch.getLastName() + toSearch.getFirstName() : "not found in the list."));
                alert.setAlertType(Alert.AlertType.INFORMATION);
            }

            alert.showAndWait();
            updateTableView();

        } catch (ListException e) {
            alert.setContentText("Error accessing employee list: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            this.alert.setContentText("The number of employees are : " + this.employeeList.size());
            util.Utility.setEmployeeList(this.employeeList); //actualizo la lista general
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();
            updateTableView();//actualiza el contenido del tableview
            //disableButtonsIfListEmpty();
        } catch (ListException e) {
            alert.setHeaderText("Error: " + e.getMessage());
            alert.show();
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addEmployee.fxml", bp);
    }

    @javafx.fxml.FXML
    public void removeOnAction(ActionEvent actionEvent) {
        //metodo de remove
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Remove Employee");
        inputDialog.setHeaderText("Enter the ID of the employee to remove:");
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
            Employee toRemove = null;

            for (int i = 1; i <= employeeList.size(); i++) {
                Employee employee = (Employee) employeeList.getNode(i).data;
                if (util.Utility.compare(employee.getId(), idToRemove)==0) {
                    toRemove = employee;
                    break;
                }
            }

            if (toRemove == null) {
                alert.setContentText("No employee found with ID: " + idToRemove);
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.showAndWait();
                return;
            }

            final Employee finalToRemove = toRemove;

            //alerta que confirma que si quiero eliminar al empleado con ese id
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete this employee?");
            confirmAlert.setContentText("ID: " + finalToRemove.getId() +
                    "\nName: " + finalToRemove.getFirstName() + " " + finalToRemove.getLastName());
            //es el boton que elimina al empleado con el id
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        employeeList.remove(finalToRemove);
                        util.Utility.setEmployeeList(employeeList);
                        alert.setContentText("Employee with ID " + finalToRemove.getId() + " was successfully removed.");
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.showAndWait();
                        updateTableView();
                    } catch (ListException e) {
                        alert.setContentText("Error removing employee: " + e.getMessage());
                        alert.setAlertType(Alert.AlertType.ERROR);
                        alert.showAndWait();
                    }
                }
            });

        } catch (ListException e) {
            alert.setContentText("Error accessing employee list: " + e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void removeLastOnAction(ActionEvent actionEvent) {
        try {
            this.employeeList.removeLast();
            util.Utility.setEmployeeList(this.employeeList); //actualizo la lista general
            this.alert.setContentText("The last element was deleted");
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();
            updateTableView(); //actualiza el contenido del tableview
        } catch (ListException e) {
            alert.setContentText("Error: "+e.getMessage());
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.showAndWait();
        }
    }

    @javafx.fxml.FXML
    public void getPrevOnAction(ActionEvent actionEvent) {
        try {
            Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                alert.setContentText("Please select an employee first.");
                alert.setAlertType(Alert.AlertType.WARNING);
            } else {
                Object prev = this.employeeList.getPrev(selected);
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
    public void sortByIdOnAction(ActionEvent actionEvent) {
        try {
            this.employeeList.sort();
            this.alert.setContentText("ORDERED LIST");
            util.Utility.setEmployeeList(this.employeeList); //actualizo la lista general
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();
            updateTableView();//actualiza el contenido del tableview
            //disableButtonsIfListEmpty();
        } catch (ListException e) {
            alert.setHeaderText("Error : " + e.getMessage());
            alert.show();
        }
    }


    @javafx.fxml.FXML
    public void sortByNameOnAction(ActionEvent actionEvent) {
            try {
                employeeList.sortByFirstName(); // tu método personalizado

                this.alert.setContentText("EMPLOYEE LIST ORDERED BY NAME");
                this.alert.setAlertType(Alert.AlertType.INFORMATION);
                this.alert.showAndWait();

                util.Utility.setEmployeeList(employeeList); // actualizo la lista en Utility
                updateTableView(); // actualiza el contenido del TableView

                // Opcional: disableButtonsIfListEmpty();

            } catch (ListException e) {
                alert.setHeaderText("Error: " + e.getMessage());
                alert.show();
            }
    }

    @javafx.fxml.FXML
    public void getNextOnAction(ActionEvent actionEvent) {
        //get next
        try {
            Employee selected = employeeTableView.getSelectionModel().getSelectedItem();
            if (selected == null) {
                alert.setContentText("Please select an employee first.");
                alert.setAlertType(Alert.AlertType.WARNING);
            } else {
                Object next = this.employeeList.getNext(selected);
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