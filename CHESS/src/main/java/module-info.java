module org.example.loginpage {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
   // requires mysql.connector.j;
    requires java.sql;
    requires java.desktop;
    requires jdk.accessibility;

    opens org.example.loginpage to javafx.fxml;
    exports org.example.loginpage;
    exports org.example.loginpage.gameLoader;
    opens org.example.loginpage.gameLoader to javafx.fxml;
}