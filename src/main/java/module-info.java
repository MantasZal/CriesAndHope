module org.example.criesandhope {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;
    requires jakarta.persistence;
    requires javafx.graphics;
    requires org.example.criesandhope;

    opens org.example.criesandhope to javafx.fxml;
    exports org.example.criesandhope;
    exports org.example.criesandhope.fxController;
    opens org.example.criesandhope.fxController to javafx.fxml;
}