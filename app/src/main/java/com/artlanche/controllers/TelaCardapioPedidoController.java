package com.artlanche.controllers;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.artlanche.model.entities.Cardapio;
import com.artlanche.model.transaction.CardapioDAO;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    private ObservableList<String> listaItens;

    @FXML
    private Label valorPorUnidade;

    private Cardapio item;

    private TelaNovoPedidoController novoPedidoController;

    private String textoLabel;

    @FXML
    void selecionarItem(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {

            textoLabel = valorPorUnidade.getText();
            listaItens = FXCollections.observableArrayList();
            List<String> itensConsultados = CardapioDAO.getListaCardapio();
            if (itensConsultados != null && !itensConsultados.isEmpty()) {
                listaItens.addAll(itensConsultados);
                itensCardapio.setItems(listaItens);
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
                    String descricao = itensCardapio.getSelectionModel().getSelectedItem();
                    if (descricao != null && !descricao.isBlank()) {
                        item = CardapioDAO.getItemCardapioByDescricao(descricao);
                        valorPorUnidade.setText(textoLabel + " " + item.getValorPorUnidade());
                    }
                }
            });
        });
    }

    public void setMainController(TelaNovoPedidoController telaNovoPedidoController) {
        if (telaNovoPedidoController != null) {
            this.novoPedidoController = telaNovoPedidoController;
        }
    }
}
