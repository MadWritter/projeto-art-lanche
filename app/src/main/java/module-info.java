module com.artlanche {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;

    opens com.artlanche to javafx.fxml;
    exports com.artlanche;
}
