package com.artlanche;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
     * Realiza a lógica de fazer cadastro
     * Na prática, deve carregar outro layout e outro Controller
     * @param event - evento de clique do botão "Cadastre-se" na janela
     */
    @FXML
    void fazerCadastro(ActionEvent event) {

    }

    @FXML
    void fazerLogin(ActionEvent event) {

    }

}
