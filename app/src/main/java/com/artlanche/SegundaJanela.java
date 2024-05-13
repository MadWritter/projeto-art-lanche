package com.artlanche;

import java.net.URL;

import com.artlanche.view.tools.Layout;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SegundaJanela {

    static Stage tela;

    /**
     * Gera uma nova janela para receber os dados do cardápio
     * @throws Exception - caso não carregue o layout da janela
     */
    public static void criar(URL layout, String titulo) throws Exception {
        tela = new Stage();

        Parent root = FXMLLoader.load(Layout.loader("TelaCardapio.fxml"));
        Image icone = new Image(Layout.iconLoader("hamburguer.png"));

        /**
         * Toma a janela principal como dona dessa janela
         * isso evita fazer operações na tela principal com essa segunda
         * janela aberta
         */
        tela.initModality(Modality.WINDOW_MODAL);
        tela.initOwner(App.getTela());

        tela.setTitle(titulo);
        tela.getIcons().add(icone);
        tela.setScene(new Scene(root));
        tela.setResizable(false);
        tela.show();
    }

    /**
     * Fecha a janela do cardápio
     */
    public static void fechar() {
        if (tela != null) {
            tela.close();
        }
    }
}
