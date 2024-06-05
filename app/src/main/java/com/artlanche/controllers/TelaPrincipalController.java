package com.artlanche.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.artlanche.App;
import com.artlanche.SegundaJanela;
import com.artlanche.model.entities.Caixa;
import com.artlanche.model.entities.Usuario;
import com.artlanche.model.transaction.CaixaDAO;
import com.artlanche.view.tools.Layout;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

/**
 * Controller que recebe os eventos da tela principal
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class TelaPrincipalController implements Initializable {

    private static Usuario usuarioAtual;

    public static Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    public static void setUsuarioAtual(Usuario usuarioAtual) {
        if (usuarioAtual != null) {
            TelaPrincipalController.usuarioAtual = usuarioAtual;
        }
    }

    public Caixa consultaCaixa = null;

    public static SegundaJanela<?> novoCaixa;

    @FXML
    void novoCaixa(ActionEvent event) throws Exception {
        novoCaixa = new SegundaJanela<AberturaCaixaController>("AberturaCaixa.fxml", "Novo Caixa");
    }

    /**
     * Esse método é carregado ao inicializar esse controller
     * Busca resposta da camada model em outra thread sobre um
     * caixa existente
     */
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
                        try {
                            URL layout = Layout.loader("TelaOp.fxml");
                            System.out.println("Layout carregado: " + layout.toExternalForm());
                            Parent telaop = FXMLLoader.load(layout);
                            App.getTela().setScene(new Scene(telaop));
                            App.getTela().centerOnScreen();
                        } catch(Exception e) {
                            throw new RuntimeException(e);
                        }
                    });
                } else {
                    contador++;
                }

                try {
                    Thread.sleep(3000); // consultar a cada 3 segundos
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