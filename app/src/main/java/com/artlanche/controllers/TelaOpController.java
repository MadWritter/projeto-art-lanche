package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import lombok.Getter;

@Getter
public class TelaOpController implements Initializable {

    @FXML
    private TextArea campoComanda;

    @FXML
    private Label campoDesconto;

    @FXML
    private TextArea campoItensCardapio;

    @FXML
    private TextArea campoNumeroPedido;

    @FXML
    private Label campoTotal;

    @FXML
    private Label campoValor;

    @FXML
    private Label labelUsuario;

    @FXML
    private ListView<String> listaDePedidos;

    private TelaPrincipalController telaPrincipalController;

    @FXML
    void alterarPedido(ActionEvent event) {

    }

    @FXML
    void cardapio(ActionEvent event) throws Exception {
        
    }

    @FXML
    void contato(ActionEvent event) {

    }

    @FXML
    void excluirPedido(ActionEvent event) {

    }

    @FXML
    void fecharCaixa(ActionEvent event) {

    }

    @FXML
    void fecharPrograma(ActionEvent event) {
        Alert alertaSair = new Alert(AlertType.CONFIRMATION);
        alertaSair.setTitle("Aviso");
        alertaSair.setHeaderText("Tem certeza que quer encerrar?");

        alertaSair.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    @FXML
    void finalizarPedido(ActionEvent event) {

    }

    @FXML
    void novoPedido(ActionEvent event) throws Exception {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            String usuarioAtual = telaPrincipalController.getUsuarioAtual().getNome();
            String textoLabel = labelUsuario.getText();
            labelUsuario.setText(textoLabel + " " + usuarioAtual);
        });
    }

    public void setMainController(TelaPrincipalController telaPrincipalController) {
        if (telaPrincipalController != null) {
            this.telaPrincipalController = telaPrincipalController;
        }
    }

}
