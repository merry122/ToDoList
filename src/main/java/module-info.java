module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens miniProject to javafx.fxml;
    exports miniProject;
}