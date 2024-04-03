package com.artlanche.controllers;

import java.io.IOException;

import javax.swing.JOptionPane;

import com.artlanche.App;
import com.artlanche.model.entities.Usuario;
import com.artlanche.model.transaction.Login;
import com.artlanche.view.Layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    @SuppressWarnings("unused")
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
    @SuppressWarnings("unused")
    @FXML
    void fazerLogin(ActionEvent event) {
        String login = campoLogin.getText();
        String senha = campoSenha.getText();
        login(login, senha);
    }

    /**
     * Método que irá fazer o login
     * Deve ser chamado ao apertar enter em um dos campos
     * ou ao clicar no botão fazer login
     */
    @FXML
    void login(String login, String senha) {

        Usuario usuarioConsultado = Login.fazerLogin(login, senha);

        if (usuarioConsultado != null) {
            JOptionPane.showConfirmDialog(null, "Bem vindo, " + usuarioConsultado.getNome() , "Autenticado com sucesso!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
            Parent telaPrincipal;
            try {
                telaPrincipal = FXMLLoader.load(Layout.loader("TelaPrincipal.fxml"));
            } catch (IOException e) {
                throw new RuntimeException("Não foi possível carregar o layout TelaPrincipal.fxml" + e.getMessage());
            }
            App.getTela().setScene(new Scene(telaPrincipal));
            App.getTela().centerOnScreen();
        } else {
            JOptionPane.showMessageDialog(null, "Dados incorretos, verifique os campos e tente novamente", "Erro de Autenticação", JOptionPane.ERROR_MESSAGE);
        }

    }

}
