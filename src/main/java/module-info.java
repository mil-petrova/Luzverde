module org.example.luzverde {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.compiler;
    requires mysql.connector.j;



    opens org.example.luzverde to javafx.fxml;
    exports org.example.luzverde;
}