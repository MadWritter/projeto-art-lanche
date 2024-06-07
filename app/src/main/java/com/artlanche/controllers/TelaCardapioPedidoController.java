package com.artlanche.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.artlanche.model.dtos.CardapioDTO;
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

    private List<CardapioDTO> itensConsultados;

    @FXML
    private Label valorPorUnidade;

    private CardapioDTO item;

    private TelaNovoPedidoController novoPedidoController;

    private String textoLabel;

    @FXML
    void selecionarItem(ActionEvent event) {
        if (item != null) {
            novoPedidoController.setItem(item);
            novoPedidoController.itemAdicionado();
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setHeaderText("Item adicionado com sucesso!");
            alerta.setTitle("Aviso");
            novoPedidoController.getStageCardapioPedidoController().close();
            alerta.showAndWait();
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Selecione um item na lista primeiro");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textoLabel = valorPorUnidade.getText();
        itensConsultados = CardapioDAO.getListaCardapio();
        if (itensConsultados != null && !itensConsultados.isEmpty()) {
            List<String> descricao = itensConsultados.stream().map(i -> i.getDescricaoItem())
                    .collect(Collectors.toList());
            itensCardapio.getItems().addAll(descricao);
            itensCardapio.refresh();
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Adicione itens no cardápio primeiro!");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
            novoPedidoController.getStageCardapioPedidoController().close();
        }

        itensCardapio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selecionado = itensCardapio.getSelectionModel().getSelectedItem();
                if (selecionado != null && !selecionado.isBlank()) {
                    item = itensConsultados.stream().filter(i -> i.getDescricaoItem().equals(selecionado))
                            .collect(Collectors.toList()).get(0);
                    String valorBr = Double.toString(item.getValorPorUnidade()).replace(".", ",");
                    if (valorBr.endsWith("0") && valorBr.charAt(valorBr.length() - 2) == ',') {
                        valorBr = valorBr.concat("0");
                    } else if (valorBr.endsWith("5") && valorBr.charAt(valorBr.length() - 2) == ',') {
                        valorBr = valorBr.concat("0");
                    }
                    valorPorUnidade.setText(textoLabel + " R$ " + valorBr);
                } else {
                    valorPorUnidade.setText(textoLabel);
                }
            }
        });
    }

    public void setMainController(TelaNovoPedidoController telaNovoPedidoController) {
        if (telaNovoPedidoController != null) {
            this.novoPedidoController = telaNovoPedidoController;
        }
    }
}
