module com.artlanche {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive jakarta.persistence;
    requires transitive lombok;
    requires transitive java.sql;
    requires transitive java.desktop;
    requires transitive org.hibernate.orm.core;
    
    opens com.artlanche.controllers to javafx.fxml;
    opens com.artlanche.model.entities;
    exports com.artlanche;
}
