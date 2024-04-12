package com.artlanche.controllers;

import com.artlanche.JanelaCaixa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
/**
 * Classe controller dos eventos de abertura do caixa
 * @since 1.0
 * @author Jean Maciel
 */
public class AberturaCaixaController {

    @FXML
    private DatePicker DataAbertura;

    @FXML
    private TextField campoValorInicial;

    @FXML
    void cancelarAbertura(ActionEvent event) {
        JanelaCaixa.fecharJanelaCaixa();
    }

    @FXML
    void confirmarAbertura(ActionEvent event) {
        //TODO criar a entidade baseada na data e valor inicial do caixa
    }

}
