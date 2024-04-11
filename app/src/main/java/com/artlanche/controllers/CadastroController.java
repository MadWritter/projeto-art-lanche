package com.artlanche.controllers;

import com.artlanche.App;
import com.artlanche.model.transaction.Cadastrar;
import com.artlanche.view.Layout;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.io.IOException;

/**
 * Controller do layout de cadastro do sistema
 *
 * @since 1.0
 * @author Jean Maciel
 */
public class CadastroController {

    @FXML
    private Button botaoCadastro;

    @FXML
    private Button botaoVoltar;

    @FXML
    private TextField campoCPF;

    @FXML
    private TextField campoEmail;

    @FXML
    private TextField campoLogin;

    @FXML
    private TextField campoNome;

    @FXML
    private PasswordField campoSenha;

    /**
     * Chama a função para realizar o cadastro
     * @param event - evento de clique do botão cadastrar
     */
    @FXML
    void fazerCadastro(ActionEvent event) {
        String nome = campoNome.getText();
        String cpf = campoCPF.getText();
        String email = campoEmail.getText(); // email não é obrigatório
        String login = campoLogin.getText();
        String senha = campoSenha.getText();

        if (nome.isBlank() || cpf.isBlank() || login.isBlank() || senha.isBlank()) {
            JOptionPane.showMessageDialog(null, "Um dos campos obrigatórios está vazio", "Alerta", JOptionPane.ERROR_MESSAGE);
        } else {
            boolean cadastrou = Cadastrar.novoUsuario(nome, cpf, email, login, senha);

            if (cadastrou) {
                JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!\nBem vindo, " + nome, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                Parent telaInicial;
                try {
                    telaInicial = FXMLLoader.load(Layout.loader("AppLayout.fxml"));
                } catch (IOException e) {
                    throw new RuntimeException("Arquivo AppLayout.fxml não foi encontrado");
                }
                App.getTela().setScene(new Scene(telaInicial));
                App.getTela().centerOnScreen();
            }
        }
    }

    /**
     * Chama para a tela inicial de login
     * @param event - evento de clique do botão voltar
     */
    @FXML
    void voltarTelaInicial(ActionEvent event) {
        Parent telaInicial;
        try {
            telaInicial = FXMLLoader.load(Layout.loader("AppLayout.fxml"));
        } catch (IOException e) {
            throw new RuntimeException("Arquivo AppLayout.fxml não foi encontrado");
        }
        App.getTela().setScene(new Scene(telaInicial));
        App.getTela().centerOnScreen();
    }

}
