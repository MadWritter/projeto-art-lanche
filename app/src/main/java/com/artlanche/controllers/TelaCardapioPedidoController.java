package com.artlanche.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.artlanche.model.entities.Cardapio;
import com.artlanche.model.transaction.CardapioDAO;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TelaCardapioPedidoController implements Initializable {

    @FXML
    private ListView<String> itensCardapio;

    @FXML
    private Label valorPorUnidade;

    static Cardapio item;

    private String textoLabel;

    @FXML
    void selecionarItem(ActionEvent event) {
        String selecionado = itensCardapio.getSelectionModel().getSelectedItem();
        if (selecionado == null || selecionado.isBlank()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso!");
            alerta.setHeaderText("Selecione um item primeiro!");
            alerta.showAndWait();
        } else {
            item = CardapioDAO.getItemCardapioByDescricao(selecionado);
            TelaNovoPedidoController.atualizou = true;
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Adicionado com sucesso!");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textoLabel = valorPorUnidade.getText();
        List<String> itensConsultados = CardapioDAO.getListaCardapio();
        if (itensConsultados == null || itensConsultados.isEmpty()) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Aviso!");
            alerta.setHeaderText("Adicione itens no cardápio primeiro!");
            alerta.showAndWait();
        } else {
            if (!itensConsultados.isEmpty()) {
                itensCardapio.getItems().addAll(itensConsultados);
            }
        }

        itensCardapio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
           @Override
           public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
               String descricao = itensCardapio.getSelectionModel().getSelectedItem();
               if (descricao != null && !descricao.isBlank()) {
                    Cardapio itemCardapio = CardapioDAO.getItemCardapioByDescricao(descricao);
                    if (itemCardapio != null) {
                        String valorDoItem = textoLabel + " R$ " + itemCardapio.getValorPorUnidade();
                        valorPorUnidade.setText(valorDoItem);
                    }
               }
           } 
        });
    }
}
