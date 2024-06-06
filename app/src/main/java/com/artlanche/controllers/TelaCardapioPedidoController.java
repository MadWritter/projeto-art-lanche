package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.artlanche.model.entities.Cardapio;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TelaCardapioPedidoController implements Initializable {

    @FXML
    private ListView<String> itensCardapio;

    @FXML
    private Label valorPorUnidade;

    static Cardapio item;

    @FXML
    void selecionarItem(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
