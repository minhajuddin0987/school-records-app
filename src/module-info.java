module acs103.t10.game {
    requires java.logging;
    requires java.sql;
    requires java.sql.rowset;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires javafx.media;
    requires org.postgresql.jdbc;

    opens Quixo.Controller to javafx.fxml;
    exports Quixo; // ✅ Export the Quixo package
}
