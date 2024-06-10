package com.artlanche.controllers;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.transaction.CardapioDAO;
import com.artlanche.view.tools.Layout;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class TelaCardapioController implements Initializable {

    @FXML
    private ListView<String> itensCardapio;

    private List<CardapioDTO> consulta;

    @FXML
    private Label valorPorUnidade;

    private String textoLabel;

    private TelaAdicionarCardapioController telaAdicionarCardapio;

    private TelaAlterarCardapioController telaAlterarCardapio;

    private Stage stageAlterarCardapio;

    private Stage stageAdicionarCardapio;

    public Stage getStageAdicionarCardapio() {
        return stageAdicionarCardapio;
    }

    public Stage getStageAlterarCardapio() {
        return stageAlterarCardapio;
    }

    private Parent esteRoot;

    public void setParent(Parent root) {
        if (root != null) {
            esteRoot = root;
        }
    }

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaAdicionarCardapio.fxml"));
        Parent root = fxml.load();

        telaAdicionarCardapio = fxml.getController();
        telaAdicionarCardapio.setMainController(this);

        stageAdicionarCardapio = new Stage();
        stageAdicionarCardapio.setScene(new Scene(root));
        stageAdicionarCardapio.initModality(Modality.WINDOW_MODAL);
        stageAdicionarCardapio.initOwner(esteRoot.getScene().getWindow());
        stageAdicionarCardapio.setTitle("Adicionar Item");
        stageAdicionarCardapio.show();
    }

    @FXML
    void alterarItem(ActionEvent event) throws Exception {
        String selecionado = itensCardapio.getSelectionModel().getSelectedItem();
        if (selecionado != null && !selecionado.isBlank()) {
            CardapioDTO dto = consulta.stream().filter(i -> i.getDescricaoItem().equals(selecionado)).collect(Collectors.toList()).get(0);
            FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaAlterarCardapio.fxml"));
            Parent root = fxml.load();

            telaAlterarCardapio = fxml.getController();
            telaAlterarCardapio.setMainController(this);
            telaAlterarCardapio.setItem(dto);

            stageAlterarCardapio = new Stage();
            stageAlterarCardapio.setScene(new Scene(root));
            stageAlterarCardapio.initModality(Modality.WINDOW_MODAL);
            stageAlterarCardapio.initOwner(esteRoot.getScene().getWindow());
            stageAlterarCardapio.setTitle("Alterar Item");
            stageAlterarCardapio.show();
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Selecione um item na lista primeiro!");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }
    }

    @FXML
    void excluirItem(ActionEvent event) {
        String selecionado = itensCardapio.getSelectionModel().getSelectedItem();
        if (selecionado != null && !selecionado.isBlank()) {
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setHeaderText("Tem certeza que deseja remover esse item?");
            alerta.setTitle("Aviso");
            Optional<ButtonType> escolha = alerta.showAndWait();
            if (escolha.isPresent() && escolha.get() == ButtonType.OK) {
                CardapioDAO.excluirItem(selecionado);
                atualizou();
            }
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Selecione um item na lista primeiro!");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        textoLabel = valorPorUnidade.getText();
        consulta = CardapioDAO.getListaCardapio();
        if (consulta != null && !consulta.isEmpty()) {
            List<String> descricao = consulta.stream().map(i -> i.getDescricaoItem()).collect(Collectors.toList());
            itensCardapio.getItems().addAll(descricao);
            itensCardapio.refresh();
        } else {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setHeaderText("Sem itens no Cardápio");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }

        itensCardapio.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selecionado = itensCardapio.getSelectionModel().getSelectedItem();
                if (selecionado != null && !selecionado.isBlank()) {
                    CardapioDTO item = consulta.stream().filter(i -> i.getDescricaoItem().equals(selecionado)).collect(Collectors.toList()).get(0);
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

    @FXML
    void atualizarLista(ActionEvent event) {
        if (itensCardapio != null || !itensCardapio.getItems().isEmpty()) {
            itensCardapio.getItems().clear();
            consulta = CardapioDAO.getListaCardapio();
            if (consulta != null && !consulta.isEmpty()) {
                List<String> descricao = consulta.stream().map(i -> i.getDescricaoItem()).collect(Collectors.toList());
                itensCardapio.getItems().addAll(descricao);
                itensCardapio.refresh();
            }
        }
    }

    public void atualizou() {
        if (itensCardapio != null || !itensCardapio.getItems().isEmpty()) {
            itensCardapio.getItems().clear();
            consulta = CardapioDAO.getListaCardapio();
            if (consulta != null && !consulta.isEmpty()) {
                List<String> descricao = consulta.stream().map(i -> i.getDescricaoItem()).collect(Collectors.toList());
                itensCardapio.getItems().addAll(descricao);
                itensCardapio.refresh();
            }
        }
    }
}
