module com.ue.insw.proyecto {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires com.fasterxml.jackson.databind;
    opens com.ue.insw.proyecto.exercises.json to com.google.gson;


}