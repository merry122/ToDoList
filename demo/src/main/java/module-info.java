module com.example.miniProject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens miniProject to javafx.fxml;
    exports miniProject;
}