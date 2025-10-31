//module org.example.criesandhope {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires org.kordamp.bootstrapfx.core;
//    requires static lombok;
//    requires jakarta.persistence;
//    requires javafx.graphics;
//
//    opens org.example.criesandhope to javafx.fxml;
//    exports org.example.criesandhope;
//    exports org.example.criesandhope.fxController;
//    opens org.example.criesandhope.fxController to javafx.fxml;
//    opens org.example.criesandhope.model to jakarta.persistence;
//    exports org.example.criesandhope.model;
//}
module com.example.courseprifs {
        requires javafx.controls;
        requires javafx.fxml;
        requires lombok;
        requires org.hibernate.orm.core;
        requires java.sql;
        requires java.naming;
        requires mysql.connector.j;
        requires jakarta.persistence;
//    Optional thingies
        requires org.controlsfx.controls;
        requires com.dlsc.formsfx;
        requires org.kordamp.bootstrapfx.core;

        opens org.example.criesandhope to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
        exports org.example.criesandhope;
        opens org.example.criesandhope.fxController to javafx.fxml;
        exports org.example.criesandhope.fxController;
        opens org.example.criesandhope.model to org.hibernate.orm.core;
        exports org.example.criesandhope.model;
        }