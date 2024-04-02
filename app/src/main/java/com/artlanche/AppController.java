package com.artlanche;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
                login();
            }
        });

        campoSenha.setOnKeyPressed(evento -> {
            if (evento.getCode() == KeyCode.ENTER) {
                login();
            }
        });
    }

    /**
     * Realiza a lógica de fazer cadastro quando clicar no botão Cadastre-se
     * Na prática, deve carregar outro layout e outro Controller
     * @param event - evento de clique do botão "Cadastre-se" na janela
     */
    @FXML
    void fazerCadastro(ActionEvent event) {

    }

    /**
     * Realiza a lógica ao clicar no botão de fazer login
     * @param event - que é disparado ao clicar no botão
     */
    @FXML
    void fazerLogin(ActionEvent event) {
        login();
    }

    /**
     * Método que irá fazer o login
     * Deve ser chamado ao apertar enter em um dos campos
     * ou ao clicar no botão fazer login
     */
    @FXML
    void login() {
        System.out.println("Funcionando!");
    }

}
