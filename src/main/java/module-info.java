module org.example.criesandhope {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires static lombok;

    opens org.example.criesandhope to javafx.fxml;
    exports org.example.criesandhope;
}