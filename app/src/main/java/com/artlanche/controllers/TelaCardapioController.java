package com.artlanche.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.artlanche.SegundaJanela;
import com.artlanche.model.transaction.CardapioDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;

public class TelaCardapioController implements Initializable{

    private static SegundaJanela segundaJanela;

    public static SegundaJanela getSegundaJanela() {
        return segundaJanela;
    }

    @FXML
    private ListView<String> itensCardapio;

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        segundaJanela = new SegundaJanela("TelaAdicionarCardapio.fxml", "Cardápio");
    }

    @FXML
    void alterarItem(ActionEvent event) throws Exception {
        String item = itensCardapio.getSelectionModel().getSelectedItem();
        if (item == null) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Selecione um item da lista primeiro!");
            alerta.showAndWait();
        } else {
            TelaAlterarCardapioController.setItem(item);
            segundaJanela = new SegundaJanela("TelaAlterarCardapio.fxml", "Cardápio");
        }
    }

    @FXML
    void excluirItem(ActionEvent event) {
        String item = itensCardapio.getSelectionModel().getSelectedItem();
        if (item == null) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Selecione um item da lista primeiro!");
            alerta.showAndWait();
        } else {
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Tem certeza que deseja remover esse item?");

            Optional<ButtonType> escolha = alerta.showAndWait();

            if (escolha.isPresent() && escolha.get() == ButtonType.OK) {
                CardapioDAO.excluirItem(item);
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (itensCardapio != null || !itensCardapio.getItems().isEmpty()) {
            itensCardapio.getItems().clear();
        }
        List<String> cardapioConsultado = CardapioDAO.getListaCardapio();
        if (cardapioConsultado == null || cardapioConsultado.isEmpty()) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Não tem nenhum item no cardápio");
            alerta.showAndWait();
        } else {
            for(String item: cardapioConsultado) {
                itensCardapio.getItems().addAll(item);
            }
        }
    }

    @FXML
    void atualizarLista(ActionEvent event) {
        if (itensCardapio != null || !itensCardapio.getItems().isEmpty()) {
            itensCardapio.getItems().clear();
        }
        List<String> cardapioConsultado = CardapioDAO.getListaCardapio();
        if (cardapioConsultado == null) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Não tem nenhum item no cardápio");
            alerta.showAndWait();
        } else {
            for(String item: cardapioConsultado) {
                itensCardapio.getItems().addAll(item);
            }
        }
    }
}
