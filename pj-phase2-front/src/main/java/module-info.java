module front {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires java.net.http;
    requires org.yaml.snakeyaml;
    requires java.desktop;

    opens front.app to javafx.fxml;
    opens front.app.fxml_controllers.requests to javafx.fxml;
    opens front.app.fxml_controllers to javafx.fxml;
    opens front.app.views to javafx.fxml;
    exports front.app;

    exports front.commons.data_class;
    exports front.commons.enums;
    exports front.commons.gson;
}

