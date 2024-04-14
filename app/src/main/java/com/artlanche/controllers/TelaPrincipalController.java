package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.artlanche.JanelaCaixa;
import com.artlanche.model.entities.Caixa;
import com.artlanche.model.entities.Usuario;
import com.artlanche.model.transaction.CaixaDAO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * Controller que recebe os eventos da tela principal
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class TelaPrincipalController implements Initializable {

    // TODO atualizar a view baseada em um caixa existente

    public static Usuario usuarioAtual;

    public Caixa consultaCaixa = null;

    @FXML
    private AnchorPane painel;

    @FXML
    private Label rotuloCaixaFechado;

    @FXML
    void caixaExistente(ActionEvent event) {

    }

    @FXML
    void novoCaixa(ActionEvent event) throws Exception {
        JanelaCaixa.criarJanelaCaixa();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Thread thread = new Thread(() -> {
            int contador = 0;

            while(consultaCaixa == null) {
                consultaCaixa = CaixaDAO.verificarSeCaixaAberto();

                if (consultaCaixa != null) {
                    if (contador == 0) {
                        Platform.runLater(() -> {
                            Alert alerta = new Alert(AlertType.INFORMATION);
                            alerta.setTitle("Aviso!");
                            alerta.setHeaderText("Já existe um caixa aberto!");
                            alerta.showAndWait();
                        });
                    }

                    Platform.runLater(() -> {
                        painel.getChildren().remove(rotuloCaixaFechado);
                    });
                } else {
                    contador++;
                }

                try {
                    Thread.sleep(5000);
                } catch(InterruptedException e) {
                    System.out.println("Saiu da Thread");
                }
            }
        });

        thread.start();
    }

    /**
     * Configura o evento de sair da tela principal
     * 
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