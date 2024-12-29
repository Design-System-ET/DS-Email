module com.design_system.ds.email {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.design_system.ds.email to javafx.fxml;
    exports com.design_system.ds.email;
    requires com.fasterxml.jackson.databind;
    requires java.base;
}
