package controller;

import domain.CircularDoublyLinkedList;
import domain.JobPosition;
import domain.ListException;
import domain.Staffing;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.Optional;

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
        //metodo de remove
        TextInputDialog inputDialog = new TextInputDialog();
        inputDialog.setTitle("Remove Staff Assignment");
        inputDialog.setHeaderText("Enter the ID of the Staff Assignment to remove:");
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
            Staffing toRemove = null;

            for (int i = 1; i <= staffAssignmentList.size(); i++) {
                Staffing staffing = (Staffing) staffAssignmentList.getNode(i).data;
                if (util.Utility.compare(staffing.getId(), idToRemove)==0) {
                    toRemove = staffing;
                    break;
                }
            }

            if (toRemove == null) {
                alert.setContentText("No Staff Assignment found with ID: " + idToRemove);
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.showAndWait();
                return;
            }

            final Staffing finalToRemove = toRemove;

            //alerta que confirma que si quiero eliminar al puesto de trabajo con ese id
            Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirm Deletion");
            confirmAlert.setHeaderText("Are you sure you want to delete this Staff Assignment?");
            confirmAlert.setContentText("ID: " + finalToRemove.getId());
            //es el boton que elimina al puesto de trabajo con el id
            confirmAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    try {
                        staffAssignmentList.remove(finalToRemove);
                        util.Utility.setStaffAssignmentList(staffAssignmentList);
                        alert.setContentText("Staff Assignment with ID " + finalToRemove.getId() + " was successfully removed.");
                        alert.setAlertType(Alert.AlertType.INFORMATION);
                        alert.showAndWait();
                        updateTableView();
                    } catch (ListException e) {
                        alert.setContentText("Error removing staff assignment: " + e.getMessage());
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
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "addJStaffAssignment.fxml", bp);
    }

    @javafx.fxml.FXML
    public void sizeOnAction(ActionEvent actionEvent) {
        try {
            this.alert.setContentText("The number of students is : " + this.staffAssignmentList.size());
            util.Utility.setStaffAssignmentList(this.staffAssignmentList); //actualizo la lista general
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
    public void sortEmplIdOnAction(ActionEvent actionEvent) {
        try {
            // Se copian los datos en un arrayList temporal
            ArrayList<Staffing> tempList = new ArrayList<>();
            for (int i = 1; i <= staffAssignmentList.size(); i++) {
                tempList.add((Staffing) staffAssignmentList.getNode(i).data);
            }

            // Ordenamos la lista
            tempList.sort((s1, s2) -> util.Utility.compare(s1.getEmployeeId(), s2.getEmployeeId()));

            // Limpiamos la lista original
            staffAssignmentList.clear();

            // Volvemos a insertar los elementos a la CircularList
            for (Staffing s : tempList) {
                staffAssignmentList.add(s);
            }

            // Actualizamos la lista e interfaz
            util.Utility.setStaffAssignmentList(this.staffAssignmentList);
            updateTableView();

            this.alert.setContentText("List Ordered by Employee ID (Using Utility.compare)");
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();

        } catch (ListException e) {
            alert.setHeaderText("Error: " + e.getMessage());
            alert.show();
        }

    }

    @javafx.fxml.FXML
    public void sortAssigTypeOnAction(ActionEvent actionEvent) {
        try {

            for (int i = 1; i <= staffAssignmentList.size(); i++) {
                for (int j = i + 1; j <= staffAssignmentList.size(); j++) {
                    Staffing s1 = (Staffing) staffAssignmentList.getNode(i).data;
                    Staffing s2 = (Staffing) staffAssignmentList.getNode(j).data;

                    // Compara por assignmentType usando el caso String
                    int result = util.Utility.compare(s1.getAssignmentType(), s2.getAssignmentType());

                    if (result > 0) { //HACE ORDENAMIENTO
                        // Intercambiamos los datos directamente
                        staffAssignmentList.getNode(i).data = s2;
                        staffAssignmentList.getNode(j).data = s1;
                    }
                }
            }

            alert.setContentText("Ordered by Assignment Type");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.showAndWait();
            updateTableView();
        } catch (ListException e) {
            alert.setHeaderText("Error : " + e.getMessage());
            alert.show();
        }
    }


    @javafx.fxml.FXML
    public void sortEmplNameOnAction(ActionEvent actionEvent) {
        try {
            // Se copian los datos en un arrayList temporal
            ArrayList<Staffing> tempList = new ArrayList<>();
            for (int i = 1; i <= staffAssignmentList.size(); i++) {
                tempList.add((Staffing) staffAssignmentList.getNode(i).data);
            }

            // Ordenamos la lista
            tempList.sort((s1, s2) -> util.Utility.compare(s1.getEmployeeName(), s2.getEmployeeId()));

            // Limpiamos la lista original
            staffAssignmentList.clear();

            // Volvemos a insertar los elementos a la CircularList
            for (Staffing s : tempList) {
                staffAssignmentList.add(s);
            }

            // Actualizamos la lista e interfaz
            util.Utility.setStaffAssignmentList(this.staffAssignmentList);
            updateTableView();

            this.alert.setContentText("List Ordered by Employee ID (Using Utility.compare)");
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();

        } catch (ListException e) {
            alert.setHeaderText("Error: " + e.getMessage());
            alert.show();
        }

    }

    @javafx.fxml.FXML
    public void sortJobPositionOnAction(ActionEvent actionEvent) {
        try {
            // Se copian los datos en un arrayList temporal
            ArrayList<Staffing> tempList = new ArrayList<>();
            for (int i = 1; i <= staffAssignmentList.size(); i++) {
                tempList.add((Staffing) staffAssignmentList.getNode(i).data);
            }

            // Ordenamos la lista
            tempList.sort((s1, s2) -> util.Utility.compare(s1.getJobPosition(), s2.getJobPosition()));

            // Limpiamos la lista original
            staffAssignmentList.clear();

            // Volvemos a insertar los elementos a la CircularList
            for (Staffing s : tempList) {
                staffAssignmentList.add(s);
            }

            // Actualizamos la lista e interfaz
            util.Utility.setStaffAssignmentList(this.staffAssignmentList);
            updateTableView();

            this.alert.setContentText("List Ordered by Employee ID (Using Utility.compare)");
            this.alert.setAlertType(Alert.AlertType.INFORMATION);
            this.alert.showAndWait();

        } catch (ListException e) {
            alert.setHeaderText("Error: " + e.getMessage());
            alert.show();
        }

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
