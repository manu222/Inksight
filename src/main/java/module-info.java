module app.Inksight {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires lucene.core;
    opens app.Inksight to com.google.gson;
}