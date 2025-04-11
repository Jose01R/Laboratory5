package domain;

import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

class CircularDoublyLinkedListTest {

    @Test
    void tes1(){
        CircularDoublyLinkedList list = new CircularDoublyLinkedList();

        CircularDoublyLinkedList a = new CircularDoublyLinkedList();
        CircularDoublyLinkedList b = new CircularDoublyLinkedList();
        CircularDoublyLinkedList c = new CircularDoublyLinkedList();
        CircularDoublyLinkedList d = new CircularDoublyLinkedList();

        //SE AGREGAN EMPLEADOS

        Calendar calendar = Calendar.getInstance(); //DATE EMPLOYEE

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

        //i. Agregue en la lista “a” los empleados con rango de edad entre 18 y 25
        //ii. Agregue en la lista “b” los empleados con rango de edad entre 26 y 40
        //iii. Agregue en la lista “c” los empleados con rango de edad entre 41 y 55
        //iv. Agregue en la lista “d” los empleados con rango de edad mayor a 55

        System.out.println("\n");

        //v. Muestre por consola el contenido de las listas a, b, c, d.
        try {
            a.add(showAgeList(list, "LISTA A", 18, 25));
            System.out.println(a);

            b.add(showAgeList(list, "LISTA B", 18, 25));
            System.out.println(b);

            c.add(showAgeList(list, "LISTA C", 18, 25));
            System.out.println(c);

            d.add(showAgeList(list, "LISTA D", 18, 25));
            System.out.println(d);

        } catch (ListException e) {
            throw new RuntimeException(e);
        }

        //PRUEBA ADD IN SORTED LIST
        try {
            list.sort();
            calendar.set(1975, 7, 6);
            list.addInSortedList(new Employee(16, "Filva", "Roberto", "Abogado", calendar.getTime()));
            System.out.println(list);
        } catch (ListException e) {
            throw new RuntimeException(e);
        }


    }

    private String showAgeList(CircularDoublyLinkedList list, String msg, int low, int high) throws ListException {
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


}