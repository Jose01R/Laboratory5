package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularLinkedListTest {

    @Test
    public void test1(){
        CircularLinkedList list = new CircularLinkedList();

        Calendar calendar = Calendar.getInstance();

        calendar.set(2005, 6, 1);
        list.add(new Employee(1, "Campos", "David", "Administracion", calendar.getTime()));

        calendar.set(2002, 10, 5);
        list.add(new Employee(2, "Gutierrez", "Ana", "Informatica", calendar.getTime()));

        calendar.set(2000, 8, 15);
        list.add(new Employee(3, "Campos", "David", "Ingles", calendar.getTime()));

        calendar.set(1995, 3, 8);
        list.add(new Employee(4, "Torres", "Miguel", "Turismo", calendar.getTime()));

        calendar.set(1993, 10, 30);
        list.add(new Employee(5, "Vargas", "Sofía", "Agronomía", calendar.getTime()));

        calendar.set(1989, 7, 19);
        list.add(new Employee(6, "Martínez", "Pedro", "Diseño Publicitario", calendar.getTime()));

        calendar.set(1986, 11, 5);
        list.add(new Employee(7, "González", "María", "Diseño Web", calendar.getTime()));

        calendar.set(1984, 2, 14);
        list.add(new Employee(8, "Herrera", "Jorge", "Asesor", calendar.getTime()));

        calendar.set(1981, 6, 1);
        list.add(new Employee(9, "Santos", "Laura", "Doctor", calendar.getTime()));

        calendar.set(1979, 4, 17);
        list.add(new Employee(10, "Reyes", "Andrés", "Abogado", calendar.getTime()));

        calendar.set(1985, 3, 10);
        list.add(new Employee(11, "Navarro", "Elena", "Informatica", calendar.getTime()));

        calendar.set(1991, 8, 25);
        list.add(new Employee(12, "Ortega", "Luis", "Ingles", calendar.getTime()));

        calendar.set(1972, 9, 13);
        list.add(new Employee(13, "Morales", "Patricia", "Turismo", calendar.getTime()));

        calendar.set(1970, 12, 2);
        list.add(new Employee(14, "Mendoza", "Raúl", "Agronomia", calendar.getTime()));

        calendar.set(1968, 5, 20);
        list.add(new Employee(15, "Ibarra", "Carmen", "Diseño Web", calendar.getTime()));

        calendar.set(1965, 7, 6);
        list.add(new Employee(16, "Silva", "Roberto", "Abogado", calendar.getTime()));
        System.out.println(list);

        try {
            System.out.println(showAgeList(list,"Empleados con rango de edad entre 18  y 25", 18, 25));
            System.out.println(showAgeList(list, "Empleados con rango de edad entre 26  y 40",26, 40));
            System.out.println(showAgeList(list, "Empleados con rango de edad entre 41  y 55", 41, 55));
            System.out.println(showAgeList(list, "Empleados con rango de edad mayor a 55", 56, 100));
        } catch (ListException e) {
            throw new RuntimeException(e);
        }


        try {

            printEmployeesByTitle(list, "Informatica");
            printEmployeesByTitle(list, "Administracion");
            printEmployeesByTitle(list, "Ingles");
            printEmployeesByTitle(list, "Turismo");
            printEmployeesByTitle(list, "Agronomía");
            printEmployeesByTitle(list, "Diseño Publicitario");
            printEmployeesByTitle(list, "Diseño Web");
            printEmployeesByTitle(list, "Asesor");
            printEmployeesByTitle(list, "Doctor");
            printEmployeesByTitle(list, "Abogado");



        } catch (ListException e) {
            throw new RuntimeException(e);
        }


    }

    private CircularLinkedList getTitleList(CircularLinkedList list, String title) throws ListException {
        CircularLinkedList result = new CircularLinkedList();

        for (int i = 1; i <= list.size(); i++){
            Employee employee = (Employee) list.getNode(i).data;
            if (employee.getTitle().equalsIgnoreCase(title)) {
                result.add(employee);
            }

        }

        return result;
    }

    private String showAgeList(CircularLinkedList list, String msg, int low, int high) throws ListException {
        String result = msg + "\n";

        for (int i = 1; i <= list.size(); i++){
            Employee employee = (Employee) list.getNode(i).data;
            int age = employee.getAge();
            if (age >= low && age <= high){
                result+= employee + "\n";
            }

        }

        return result;
    }


    private void printEmployeesByTitle(CircularLinkedList list, String title) throws ListException {
        CircularLinkedList result = getTitleList(list, title);
        System.out.println("Empleados con la profesión: " + title);
        System.out.println(result);
    }


}