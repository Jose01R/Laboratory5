package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AddJStaffAssignmentController {
    @javafx.fxml.FXML
    private ChoiceBox selectAssigTypeChoiceBox;
    @javafx.fxml.FXML
    private ChoiceBox selectJobPositionChoiceBox;
    @javafx.fxml.FXML
    private ChoiceBox selectSupervisorChoiceBox;
    @javafx.fxml.FXML
    private ChoiceBox selectEmployeeChoiceBox;
    @javafx.fxml.FXML
    private DatePicker dateStaffAssignment;
    @javafx.fxml.FXML
    private TextField idRegisterTextField;
    @javafx.fxml.FXML
    private BorderPane bp;

    private CircularLinkedList employeeList;
    private CircularDoublyLinkedList jobPositionsList;
    private Employee employee;
    private Staffing staffing;
    private Alert alert; //para el manejo de alertas
    private LocalDateTime registerDate;

    private StaffAssignmentController staffAssignmentController;  // Referencia al StaffAssignmentController
    private CircularDoublyLinkedList staffAssignmentList;

    @javafx.fxml.FXML
    public void initialize() {
        // Inicializar las listas
        this.employeeList = util.Utility.getEmployeeList();
        this.jobPositionsList = util.Utility.getJobPositionsList();
        this.staffAssignmentList = util.Utility.getStaffAssignmentList()  ;

        alert = util.FXUtility.alert("Staff Assignment List", "Add Staff Assignment");

        // Llamamos a los métodos para cargar los ComboBoxes
        loadEmployeeChoiceBox();
        loadJobPositionChoiceBox();
        loadSupervisorChoiceBox();
        loadAssignTypeChoiceBox();

        // Mostrar el nuevo ID disponible
        int nextId = Staffing.getAutoId() + 1;
        idRegisterTextField.setText(String.valueOf(nextId));

    }

    //METODO PARA GUARDAR HORA Y FECHA
    @javafx.fxml.FXML
    private void onDateSelected(ActionEvent event) {
        LocalDate selectedDate = dateStaffAssignment.getValue();
        LocalTime currentTime = LocalTime.now();

        if (selectedDate != null) {
            registerDate = LocalDateTime.of(selectedDate, currentTime);
            //MENSAJE DEPURACION
            System.out.println("Fecha y hora guardadas: " + registerDate);
        }
    }

    @javafx.fxml.FXML
    public void addOnAction(ActionEvent actionEvent) {

        LocalDate dateLocal = dateStaffAssignment.getValue();
        Employee selectedEmployee = (Employee) selectEmployeeChoiceBox.getValue();
        JobPosition selectedJobPosition = (JobPosition) selectJobPositionChoiceBox.getValue();
        String supervisorValue= (String)selectSupervisorChoiceBox.getValue();
        String assignTypeValue= (String)selectAssigTypeChoiceBox.getValue();


        // Crear el nuevo registro
        // Verifica si todos los campos están completos
        if (selectedJobPosition==null || selectedEmployee == null || supervisorValue==null || assignTypeValue==null || dateLocal==null ) {
            util.FXUtility.alert("ERROR", "Todos los campos deben ser completados.").showAndWait();
            return;
        }
        LocalDateTime localDateTime = dateLocal.atStartOfDay();
        Staffing staffing = new Staffing(
                localDateTime,
                selectedEmployee.getId(),
                selectedEmployee.getFirstName(),
               selectedJobPosition.getDescription(),
               supervisorValue,
               assignTypeValue
        );

        // Agregar el nuevo registro a la lista local registerList
        staffAssignmentList.add(staffing);
        util.Utility.setStaffAssignmentList(staffAssignmentList);
        if (staffAssignmentController != null) {
            try {
                staffAssignmentController.updateTableView();
            } catch (ListException e) {
                throw new RuntimeException(e);
            }
        }

        // Mostrar el nuevo ID disponible
        int nextId = Staffing.getAutoId() + 1;
        idRegisterTextField.setText(String.valueOf(nextId));

        // Limpiar los campos después de agregar el registro
        cleanOnAction(actionEvent);

        // Mostrar una alerta de éxito
        alert.setContentText("Staff added correctly");
        alert.setAlertType(Alert.AlertType.INFORMATION);
        alert.showAndWait();

    }

    @javafx.fxml.FXML
    public void cleanOnAction(ActionEvent actionEvent) {
        dateStaffAssignment.setValue(null);
        selectAssigTypeChoiceBox.getSelectionModel().clearSelection();
        selectEmployeeChoiceBox.getSelectionModel().clearSelection();
        selectJobPositionChoiceBox.getSelectionModel().clearSelection();
        selectSupervisorChoiceBox.getSelectionModel().clearSelection();
    }

    @javafx.fxml.FXML
    public void closeOnAction(ActionEvent actionEvent) {
        util.FXUtility.loadPage("ucr.lab.HelloApplication", "staffAssignment.fxml", bp);
    }

    private void loadEmployeeChoiceBox() {
        ObservableList<Employee> observableEmployee = FXCollections.observableArrayList();

        try {
            if (employeeList != null && !employeeList.isEmpty()) {
                for (int i = 1; i <= employeeList.size(); i++) {
                    Employee employee = (Employee) employeeList.getNode(i).data;
                    observableEmployee.add(employee);
                }
                selectEmployeeChoiceBox.setItems(observableEmployee);//no se si es asi
            }
        } catch (ListException e) {
            util.FXUtility.alert("ERROR", "No se pudo cargar la lista de empleados");
        }
    }


    private void loadJobPositionChoiceBox() {
        ObservableList<JobPosition> observableJobPosition = FXCollections.observableArrayList();

        try {
            if (jobPositionsList != null && !jobPositionsList.isEmpty()) {
                for (int i = 1; i <= jobPositionsList.size(); i++) {
                    JobPosition jobPosition = (JobPosition) jobPositionsList.getNode(i).data;
                    observableJobPosition.add(jobPosition);
                }
                selectJobPositionChoiceBox.setItems(observableJobPosition);
            }
        } catch (ListException e) {
            util.FXUtility.alert("ERROR", "No se pudo cargar la lista de posicion de trabajos");
        }
    }
    private void loadSupervisorChoiceBox(){
        ObservableList<String> observableListRandomNames= FXCollections.observableArrayList();
        String []apellidos={"Carvajal","Picado","Hernandez","Solano","Tenorio","Smith","Salazar","Perez","Gutierrez"};
        String []nombres={"Gerardo","Kristel","María","Roberto","Carlos","Lincy","Jose","Luis","Jared"};
        while (observableListRandomNames.size()<5){
            int randomNumberLastName =util.Utility.random(8);
            int randomNumberFirstName =util.Utility.random(8);
            String apellidoSeleccionado = apellidos[randomNumberLastName];
            String nombreSeleccionado=nombres[randomNumberFirstName];
            String fullName=apellidoSeleccionado.concat(" "+nombreSeleccionado);

            boolean alreadyExists=false;
            for (String concatenation:observableListRandomNames){
                if (concatenation.contains(fullName)){
                    alreadyExists=true;
                    break;
                }
            }
            if(!alreadyExists) {
                observableListRandomNames.add(fullName);
            }
        }
        selectSupervisorChoiceBox.setItems(observableListRandomNames);
    }
    private void loadAssignTypeChoiceBox(){

        ObservableList<String> observableListAssingType= FXCollections.observableArrayList();
        observableListAssingType.add("Promotion");
        observableListAssingType.add("Transferencia");
        observableListAssingType.add("Nuevo ingreso");
        observableListAssingType.add("Temporal");
        observableListAssingType.add("Otro");
        selectAssigTypeChoiceBox.setItems(observableListAssingType);
    }
}
