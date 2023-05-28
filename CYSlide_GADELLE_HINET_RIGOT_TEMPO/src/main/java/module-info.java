module com.example.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.javaproject to javafx.fxml;
    exports com.example.javaproject;
}