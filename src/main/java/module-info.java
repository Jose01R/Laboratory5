module ucr.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens ucr.lab to javafx.fxml;
    exports ucr.lab;
    exports controller;
    opens controller to javafx.fxml;

    /*Sin este código no carga los tableviews de Student, Course, Register*/
    opens domain to javafx.fxml;
    exports domain;
}