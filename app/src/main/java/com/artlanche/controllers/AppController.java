package com.artlanche.controllers;

import java.io.IOException;

import com.artlanche.App;
import com.artlanche.model.entities.Usuario;
import com.artlanche.model.transaction.LoginDAO;
import com.artlanche.view.tools.Layout;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

/**
 * Classe controller do Layout AppLayout.fxml
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class AppController {

    // botão Cadastre-se
    @FXML
    private Button botaoCadastro;

    // botão Fazer Login
    @FXML
    private Button botaoLogin;

    // campo de Login
    @FXML
    private TextField campoLogin;

    // campo de Senha
    @FXML
    private PasswordField campoSenha;

    @FXML
    private ImageView logo = new ImageView(Layout.iconLoader("hamburguer.png"));

    private TelaPrincipalController telaPrincipalController;

    /**
     * Recebe outras funções ao inicializar o layout
     */
    @FXML
    private void initialize() {
        campoLogin.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ENTER) {
                String login = campoLogin.getText();
                String senha = campoSenha.getText();
                login(login, senha);
            }
        });

        campoSenha.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ENTER) {
                String login = campoLogin.getText();
                String senha = campoSenha.getText();
                login(login, senha);
            }
        });
    }

    /**
     * Realiza a lógica de fazer cadastro quando clicar no botão Cadastre-se
     * Na prática, deve carregar outro layout e outro Controller
     * 
     * @param event - evento de clique do botão "Cadastre-se" na janela
     */
    @FXML
    void fazerCadastro(ActionEvent event) {
        Parent telaCadastro;
        try {
            telaCadastro = FXMLLoader.load(Layout.loader("CadastroLayout.fxml"));
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível carregar o layout CadastroLayout.fxml" + e.getMessage());
        }
        App.getTela().setScene(new Scene(telaCadastro));
        App.getTela().centerOnScreen();
    }

    /**
     * Realiza a lógica ao clicar no botão de fazer login
     * 
     * @param event - que é disparado ao clicar no botão
     */
    @FXML
    void fazerLogin(ActionEvent event) {
        Platform.runLater(() -> {
            String login = campoLogin.getText();
            String senha = campoSenha.getText();
            login(login, senha);
        });
    }

    /**
     * Método que irá fazer o login
     * Deve ser chamado ao apertar enter em um dos campos
     * ou ao clicar no botão fazer login
     */
    @FXML
    void login(String login, String senha) {
        Platform.runLater(() -> {

            Usuario usuarioConsultado = LoginDAO.fazerLogin(login, senha);
    
            if (usuarioConsultado != null) {
    
                Alert alertaLogin = new Alert(AlertType.INFORMATION);
                alertaLogin.setTitle("Autenticado com sucesso");
                alertaLogin.setHeaderText("Bem vindo, " + usuarioConsultado.getNome());
                alertaLogin.showAndWait();

                Parent root;
                try {
                    FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaPrincipal.fxml"));
                    root = fxml.load();
                    telaPrincipalController = fxml.getController();
                    telaPrincipalController.setUsuarioAtual(usuarioConsultado);
                } catch (IOException e) {
                    throw new RuntimeException("Não foi possível carregar o layout TelaPrincipal.fxml" + e.getMessage());
                }
                App.getTela().setScene(new Scene(root));
                App.getTela().centerOnScreen();
            } else {
                Alert alertaDadosIncorretos = new Alert(AlertType.ERROR);
                alertaDadosIncorretos.setTitle("Autenticação Falhou");
                alertaDadosIncorretos.setHeaderText("Dados incorretos, tente novamente");
                alertaDadosIncorretos.showAndWait();
            }
        });
    }

}
