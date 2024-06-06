package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class TelaNovoPedidoController implements Initializable {

    @FXML
    private TextField campoDesconto;

    @FXML
    private ListView<String> listaItensCardapio;

    @FXML
    private TextArea textoComanda;

    @FXML
    private TextField valorAdicional;

    private TelaOpController controllerPai;

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        
    }

    @FXML
    void finalizar(ActionEvent event) {
        
    }

    @FXML
    void removerItem(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
