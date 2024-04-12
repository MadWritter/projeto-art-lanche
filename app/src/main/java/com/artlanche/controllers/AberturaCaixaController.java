package com.artlanche.controllers;

import java.time.LocalDate;

import com.artlanche.JanelaCaixa;
import com.artlanche.model.transaction.RegistrarCaixa;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

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
        if (campoValorInicial != null && DataAbertura != null) {
            Double valorInicial;
            LocalDate dataAbertura;
            try {

                valorInicial = Double.parseDouble(campoValorInicial.getText().replace(",", "."));
                dataAbertura = DataAbertura.getValue();

                RegistrarCaixa.novoCaixa(valorInicial, dataAbertura, TelaPrincipalController.usuarioAtual.getNome());

            } catch (NumberFormatException e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("O valor do caixa inserido está incorreto");
                alerta.showAndWait();
            }
        }
    }

}
