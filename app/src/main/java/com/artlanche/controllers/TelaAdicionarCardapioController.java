package com.artlanche.controllers;

import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.transaction.CardapioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class TelaAdicionarCardapioController {

    @FXML
    private TextField campoItem;

    @FXML
    private TextField campoValorUnidade;

    private TelaCardapioController telaCardapioController;

    @FXML
    void adicionarItem(ActionEvent event) {
        String nomeItem = campoItem.getText();
        String valorString = campoValorUnidade.getText().replace(',','.');
        if (nomeItem == null || nomeItem.isBlank()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("O campo com o nome do item está vazio!");
            alerta.showAndWait();
        } else if (valorString == null || valorString.isBlank()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("O campo com o nome do item está vazio!");
            alerta.showAndWait();
        } else {
            double valorConvertido = converterValorUnidade(valorString);
            var dados = new CardapioDTO(nomeItem, valorConvertido);
            boolean registrou = CardapioDAO.adicionarItem(dados);
            if (registrou) {
                Alert alerta = new Alert(AlertType.INFORMATION);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Item inserido com sucesso!");
                telaCardapioController.atualizou();
                telaCardapioController.getStageAdicionarCardapio().close();
                alerta.showAndWait();
            } else {
                Alert itemExistente = new Alert(AlertType.ERROR);
                itemExistente.setHeaderText("A descrição desse item já existe no cardápio");
                itemExistente.setTitle("Aviso");
                itemExistente.showAndWait();
            }
        }

    }

    private double converterValorUnidade(String valorTexto) {
        try {
            return Double.parseDouble(valorTexto);
        } catch (NumberFormatException e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("O valor por unidade inserido está em formato incorreto!");
            alerta.showAndWait();
            throw new RuntimeException("Valor inserido em formato incorreto");
        }
    }

    @FXML
    void cancelar(ActionEvent event) {
        telaCardapioController.getStageAdicionarCardapio().close();
    }

    public void setMainController(TelaCardapioController telaCardapioController) {
        if (telaCardapioController != null) {
            this.telaCardapioController = telaCardapioController;
        }
    }

}
