package domain;

public class JobPosition {
    private int id;
    private String description;
    private Double hourlyWage;
    private static int autoId; //PARA EL AUTOGENERADO

    //CONSTRUCTOR 1
    public JobPosition(int id, String description, Double hourlyWage) {
        this.id = id;
        this.description = description;
        this.hourlyWage = hourlyWage;
    }

    //CONSTRUCTOR 2
    public JobPosition(String description, Double hourlyWage) {
        this.id = ++autoId;
        this.description = description;
        this.hourlyWage = hourlyWage;
    }

    //CONSTRUCTOR 3
    public JobPosition(int id) {
        this.id = id;
    }

    //GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getHourlyWage() {
        return hourlyWage;
    }

    public void setHourlyWage(Double hourlyWage) {
        this.hourlyWage = hourlyWage;
    }

    public static int getAutoId() {
        return autoId;
    }

    public static void setAutoId(int autoId) {
        JobPosition.autoId = autoId;
    }

    //METODO CALCULO SALARIO
    public Double getSalary(double n){
        return n * hourlyWage; //n = numero de horas trabajadas
    }


    @Override
    public String toString() {
        return "(ID)"+id+"/(Job Position)"+description +" /(Hourly Wage)"+ hourlyWage;
    }


}
