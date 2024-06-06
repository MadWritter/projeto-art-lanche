package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.artlanche.model.entities.Cardapio;
import com.artlanche.model.transaction.CardapioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class TelaAlterarCardapioController implements Initializable {

    @FXML
    private TextField campoItem;

    @FXML
    private TextField campoValorUnidade;

    static Cardapio item;

    String nomeAntigo;

    String valorAntigo;

    @FXML
    void alterarItem(ActionEvent event) {
        String nomeNovo = campoItem.getText();
        String valorNovo = campoValorUnidade.getText().replace(',', '.');
        if (nomeNovo == null || nomeNovo.isBlank() ||
                valorNovo == null || valorNovo.isEmpty()) {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setTitle("Aviso");
                    alerta.setHeaderText("Um ou mais campos está vazio!");
                    alerta.showAndWait();
        } else {
            try {
                double valorNovoConvertido = Double.parseDouble(valorNovo);
                boolean atualizou = CardapioDAO.alterarItem(nomeAntigo, nomeNovo, valorNovoConvertido);
                if (atualizou) {
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Aviso");
                    alerta.setHeaderText("Item Atualizado com sucesso!");
                    alerta.showAndWait();
                }
            } catch (Exception e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("O valor informado está em formato incorreto");
                alerta.showAndWait();
            }
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nomeAntigo = item.getDescricaoItem();
        double valorAntigoDouble = item.getValorPorUnidade();
        valorAntigo = Double.toString(valorAntigoDouble).replace('.', ',');

        campoItem.setText(nomeAntigo);
        campoValorUnidade.setText(valorAntigo);
    }

}
