module app.Inksight {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    opens app.Inksight to com.google.gson;
}