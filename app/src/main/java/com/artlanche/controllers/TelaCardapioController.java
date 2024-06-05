package com.artlanche.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.artlanche.SegundaJanela;
import com.artlanche.model.entities.Cardapio;
import com.artlanche.model.transaction.CardapioDAO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import lombok.Getter;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

@Getter
public class TelaCardapioController implements Initializable{

    public static SegundaJanela<?> segundaJanela;

    public static SegundaJanela<?> getSegundaJanela() {
        return segundaJanela;
    }

    @FXML
    private ListView<String> itensCardapio;

    @FXML
    private Label valorPorUnidade;

    private String textoLabel;

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        segundaJanela = new SegundaJanela<TelaAdicionarCardapioController>("TelaAdicionarCardapio.fxml", "Cardápio");
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
            Cardapio itemConsultado = CardapioDAO.getItemCardapioByDescricao(item);
            TelaAlterarCardapioController.item = itemConsultado;
            segundaJanela = new SegundaJanela<TelaAlterarCardapioController>("TelaAlterarCardapio.fxml", "Cardápio");
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

        textoLabel = valorPorUnidade.getText();
        itensCardapio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String descricaoItem = itensCardapio.getSelectionModel().getSelectedItem();
                double valorItemSelecionado = CardapioDAO.getItemCardapioByDescricao(descricaoItem).getValorPorUnidade();

                String valorAjustadoString;
                try {
                    valorAjustadoString = Double.toString(valorItemSelecionado).replace('.', ',');
                } catch(Exception e) {
                    throw e;
                }

                valorPorUnidade.setText(textoLabel + " R$ " + valorAjustadoString);
            }
        });
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
