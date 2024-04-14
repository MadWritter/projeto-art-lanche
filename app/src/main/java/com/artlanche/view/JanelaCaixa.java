package com.artlanche.view;

import com.artlanche.view.tools.Layout;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Classe que gera a janela de abertura e confirma fechamento de caixa.
 * @since 1.0
 * @author Jean Maciel
 */
public class JanelaCaixa {

    static Stage tela;

    /**
     * Gera uma nova janela para receber os dados da abertura de caixa
     * @throws Exception - caso não carregue o layout da janela
     */
    public static void criarJanelaCaixa() throws Exception {
        tela = new Stage();
        Parent root = FXMLLoader.load(Layout.loader("AberturaCaixa.fxml"));

        Image icone = new Image(Layout.iconLoader("hamburguer.png"));
        tela.setTitle("Novo Caixa");
        tela.getIcons().add(icone);
        tela.setScene(new Scene(root));
        tela.show();
    }

    /**
     * Fecha a janela que faz a abertura do caixa
     */
    public static void fecharJanelaCaixa() {
        if (tela != null) {
            tela.close();
        }
    }
    
}
