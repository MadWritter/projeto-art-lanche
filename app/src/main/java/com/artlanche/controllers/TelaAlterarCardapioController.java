package com.artlanche.controllers;

import com.artlanche.model.transaction.CardapioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class TelaAlterarCardapioController {

    @FXML
    private TextField campoItem;

    private static String item;

    public static void setItem(String itemInformado) {
        if (itemInformado != null) {
            item = itemInformado;
        }
    }

    @FXML
    void alterarItem(ActionEvent event) {
        String novoNome = campoItem.getText();
        if (novoNome == null || novoNome.isBlank()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso!");
            alerta.setHeaderText("O campo de texto está vazio!");
            alerta.showAndWait();
        } else {
            boolean alterou = CardapioDAO.alterarItem(item, novoNome);
            if (alterou) {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Sucesso!");
                alerta.setHeaderText("Descrição do item alterada com sucesso");
                alerta.showAndWait();
                TelaCardapioController.getSegundaJanela().fecharJanela();
            } else {

            }
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        TelaCardapioController.getSegundaJanela().fecharJanela();
    }

}
