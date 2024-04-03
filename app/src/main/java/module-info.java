module com.artlanche {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive jakarta.persistence;
    requires transitive lombok;
    requires transitive java.sql;
    requires transitive java.desktop;

    opens com.artlanche.controllers to javafx.fxml;
    exports com.artlanche;
}
