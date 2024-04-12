package com.artlanche.controllers;

import com.artlanche.JanelaCaixa;
import com.artlanche.model.entities.Usuario;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Controller que recebe os eventos da tela principal
 * @since 1.0
 * @author Jean Maciel
 */
public class TelaPrincipalController {


    //TODO atualizar a view baseada em um caixa existente

    public static Usuario usuarioAtual;

    @FXML
    void caixaExistente(ActionEvent event) {

    }

    @FXML
    void fecharCaixa(ActionEvent event) {

    }

    @FXML
    void novoCaixa(ActionEvent event) throws Exception {
        JanelaCaixa.criarJanelaCaixa();
    }

    /**
     * Configura o evento de sair da tela principal
     * @param event - evento de selecionar para sair
     */
    @FXML
    void sair(ActionEvent event) {
        Alert alertaSair = new Alert(AlertType.CONFIRMATION);
        alertaSair.setTitle("Aviso");
        alertaSair.setHeaderText("Tem certeza que quer encerrar?");

        alertaSair.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

}