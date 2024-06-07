package com.artlanche.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.transaction.PedidoDAO;
import com.artlanche.view.tools.Layout;

import jakarta.persistence.PersistenceException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class TelaNovoPedidoController implements Initializable {

    @FXML
    private TextField campoDesconto;

    @FXML
    private ListView<String> listaItensCardapio;

    private List<CardapioDTO> items;

    private CardapioDTO item;

    @FXML
    private TextArea textoComanda;

    @FXML
    private TextField valorAdicional;

    private TelaOpController controllerPai;

    private TelaCardapioPedidoController cardapioPedidoController;

    private Stage stageCardapioPedidoController;

    private Parent esteRoot;


    public void setItem(CardapioDTO item) {
        if (item != null) {
            this.item = item;
        }
    }

    public void setRoot(Parent root) {
        if (root != null) {
            esteRoot = root;
        }
    }

    public Stage getNovoPedidoStage() {
        return stageCardapioPedidoController;
    }

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaCardapioPedido.fxml"));
        Parent root = fxml.load();

        cardapioPedidoController = fxml.getController();
        cardapioPedidoController.setMainController(this);

        stageCardapioPedidoController = new Stage();
        stageCardapioPedidoController.setScene(new Scene(root));
        stageCardapioPedidoController.initModality(Modality.WINDOW_MODAL);
        stageCardapioPedidoController.initOwner(esteRoot.getScene().getWindow());
        stageCardapioPedidoController.show();
    }

    @FXML
    void finalizar(ActionEvent event) {
        Platform.runLater(() -> {

            try {
                Long caixaId = controllerPai.getCaixaId();
                boolean semItensOuComanda = textoComanda.getText().isBlank() && listaItensCardapio.getItems().isEmpty()
                        ? true
                        : false;
                boolean comandaSemValorAdicional = !textoComanda.getText().isBlank() && valorAdicional.getText().isBlank()
                        ? true
                        : false;
                boolean valorAdicionalSemComanda = !valorAdicional.getText().isBlank() && textoComanda.getText().isBlank()
                        ? true
                        : false;
                if (semItensOuComanda) {
                    Alert semItems = new Alert(AlertType.ERROR);
                    semItems.setHeaderText("Adicione itens do cardápio ou uma comanda");
                    semItems.setTitle("Aviso");
                    semItems.showAndWait();
                } else if (comandaSemValorAdicional) {
                    Alert semItems = new Alert(AlertType.ERROR);
                    semItems.setHeaderText("Preencha o valor da comanda");
                    semItems.setTitle("Aviso");
                    semItems.showAndWait();
                } else if (valorAdicionalSemComanda) {
                    Alert semItems = new Alert(AlertType.ERROR);
                    semItems.setHeaderText("Adicione a descrição da comanda");
                    semItems.setTitle("Aviso");
                    semItems.showAndWait();
                } else {
    
                    // comanda e valor da comanda e desconto
                    String comanda = null;
                    Double valorComanda = null;
                    Double desconto = null;
                    if (textoComanda != null && !textoComanda.getText().isBlank()) {
                        comanda = textoComanda.getText();
                    }
    
                    if (valorAdicional != null && !valorAdicional.getText().isBlank()) {
                        String valorAdicionalString = valorAdicional.getText();
                        if (valorAdicionalString.contains(",")) {
                            valorAdicionalString = valorAdicionalString.replace(",", ".");
                            valorComanda = Double.parseDouble(valorAdicionalString);
                        } else {
                            throw new NumberFormatException();
                        }
                    }
    
                    if (campoDesconto != null && !campoDesconto.getText().isBlank()) {
                        String stringDesconto = campoDesconto.getText();
                        if (stringDesconto.contains(",")) {
                            stringDesconto = stringDesconto.replace(",", ".");
                            desconto = Double.parseDouble(stringDesconto);
                        } else {
                            throw new NumberFormatException();
                        }
                    }
    
                    var pedido = new PedidoDTO(caixaId, items, comanda, valorComanda, desconto);
                    boolean cadastrou = PedidoDAO.cadastrarNovoPedido(pedido);
                    if (cadastrou) {
                        controllerPai.atualizou();
                        Alert cadastrado = new Alert(AlertType.INFORMATION);
                        cadastrado.setHeaderText("Pedido adicionado com sucesso");
                        cadastrado.setTitle("Aviso");
                        cadastrado.showAndWait();
                        controllerPai.getNovoPedidoStage().close();
                    } else {
                        throw new PersistenceException();
                    }
                }
            } catch (NumberFormatException e) {
                Alert formatoIncorreto = new Alert(AlertType.ERROR);
                formatoIncorreto.setHeaderText("O valor da comanda está em formato incorreto");
                formatoIncorreto.setTitle("Aviso");
                formatoIncorreto.showAndWait();
            } catch (PersistenceException e) {
                Alert formatoIncorreto = new Alert(AlertType.ERROR);
                formatoIncorreto.setHeaderText("Erro ao cadastrar no banco de dados, tente novamente");
                formatoIncorreto.setTitle("Aviso");
                formatoIncorreto.showAndWait();
            }
        });
    }

    @FXML
    void removerItem(ActionEvent event) {
        String itemSelecionado = listaItensCardapio.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null && !itemSelecionado.isBlank()) {
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setHeaderText("Tem certeza que deseja remover?!");
            alerta.setTitle("Aviso");
            Optional<ButtonType> resultado = alerta.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                CardapioDTO dtoParaExcluir = items.stream().filter(i -> i.getDescricaoItem().equals(itemSelecionado)).collect(Collectors.toList()).get(0);
                items.remove(dtoParaExcluir);
                listaItensCardapio.getItems().remove(itemSelecionado);
                listaItensCardapio.refresh();
            }
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Selecione um item da lista primeiro!");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }
    }

    public void setMainController(TelaOpController telaOpController) {
        if (telaOpController != null) {
            controllerPai = telaOpController;
        }
    }

    public void itemAdicionado() {
        if (item != null) {
            items.add(item);
            listaItensCardapio.getItems().addAll(item.getDescricaoItem());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        items = new ArrayList<>();
    }
}
