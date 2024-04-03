module com.artlanche {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive jakarta.persistence;
    requires transitive lombok;

    opens com.artlanche.controllers to javafx.fxml;
    exports com.artlanche;
}
