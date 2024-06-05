package com.artlanche;

import com.artlanche.view.tools.Layout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SegundaJanela<T> {

    private Stage janela;
    private FXMLLoader layoutAtual;

    public SegundaJanela(String layout, String titulo) throws Exception {
        janela = new Stage();
        layoutAtual = new FXMLLoader(Layout.loader(layout));
        Parent cena = layoutAtual.load();
        janela.setTitle(titulo);
        janela.setScene(new Scene(cena));
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

    public T getController() {
        return layoutAtual.getController();
    }
}
