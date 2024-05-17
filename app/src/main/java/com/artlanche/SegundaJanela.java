package com.artlanche;

import com.artlanche.view.tools.Layout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SegundaJanela {

    private Stage janela;

    public SegundaJanela(String layout, String titulo) throws Exception {
        janela = new Stage();
        Parent root = FXMLLoader.load(Layout.loader(layout));
        janela.setTitle(titulo);
        janela.setScene(new Scene(root));
        janela.setResizable(false);
        janela.initModality(Modality.WINDOW_MODAL);
        janela.initOwner(App.getTela());
        janela.show();
    }

    public void fecharJanela() {
        if (janela != null) {
            janela.close();
        }
    }
}
