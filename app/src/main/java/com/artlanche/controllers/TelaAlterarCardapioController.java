package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.transaction.CardapioDAO;

import javafx.application.Platform;
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

    private TelaCardapioController telaCardapioController;

    private CardapioDTO itemcCardapio;

    private String nomeAntigo;

    public void setItem(CardapioDTO dto) {
        if (dto != null) {
            itemcCardapio = dto;
        }
    }

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
                var dados = new CardapioDTO(nomeNovo, valorNovoConvertido);
                boolean atualizou = CardapioDAO.alterarItem(nomeAntigo, dados);
                if (atualizou) {
                    telaCardapioController.atualizou();
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Aviso");
                    alerta.setHeaderText("Item Atualizado com sucesso!");
                    telaCardapioController.atualizou();
                    telaCardapioController.getStageAlterarCardapio().close();
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
        telaCardapioController.getStageAlterarCardapio().close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            nomeAntigo = itemcCardapio.getDescricaoItem();
            double valorAntigoDouble = itemcCardapio.getValorPorUnidade();
            String valorAntigo = Double.toString(valorAntigoDouble).replace('.', ',');
    
            campoItem.setText(nomeAntigo);
            campoValorUnidade.setText(valorAntigo);
        });
    }

    public void setMainController(TelaCardapioController telaCardapioController) {
        if (telaCardapioController != null) {
            this.telaCardapioController = telaCardapioController;
        }
    }

}
