package com.artlanche.controllers;

import com.artlanche.model.transaction.CardapioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class TelaAdicionarCardapioController {

    @FXML
    private TextField campoItem;

    @FXML
    void adicionarItem(ActionEvent event) {
        String nomeItem = campoItem.getText();
        if (nomeItem == null || nomeItem.isBlank()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("O campo informado está vazio!");
            alerta.showAndWait();
        } else {
            boolean adicionou = CardapioDAO.adicionarItem(nomeItem);
            if (adicionou) {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Adicionado com sucesso!");
                alerta.showAndWait();
                TelaCardapioController.getSegundaJanela().fecharJanela();
            } else {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Esse item já existe no cardápio!");
                alerta.showAndWait();
            }
        }    
    }

    @FXML
    void cancelar(ActionEvent event) {
        TelaCardapioController.getSegundaJanela().fecharJanela();
    }

}
