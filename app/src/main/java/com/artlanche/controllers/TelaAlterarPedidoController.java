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

public class TelaAlterarPedidoController implements Initializable{

    @FXML
    private TextField campoDesconto;

    @FXML
    private ListView<String> listaItensCardapio;

    private List<CardapioDTO> items;

    @FXML
    private TextArea textoComanda;

    @FXML
    private TextField valorAdicional;

    private TelaOpController mainController;

    private PedidoDTO pedidoParaAlterar;

    private TelaCardapioPedidoController cardapioPedidoController;

    private Stage stageCardapioPedido;

    private Parent root;

    private CardapioDTO item;

    public void pedidoParaAlterar(PedidoDTO pedido) {
        if (pedido != null) {
            this.pedidoParaAlterar = pedido;
        }
    }

    public Stage getStageCardapioPedido() {
        return this.stageCardapioPedido;
    }

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        FXMLLoader telaAdicionarItem = new FXMLLoader(Layout.loader("TelaCardapioPedido.fxml"));
        Parent rootAdicionarItem = telaAdicionarItem.load();

        cardapioPedidoController = telaAdicionarItem.getController();
        cardapioPedidoController.setAlterarPedidoController(this);

        stageCardapioPedido = new Stage();
        stageCardapioPedido.setScene(new Scene(rootAdicionarItem));
        stageCardapioPedido.initModality(Modality.WINDOW_MODAL);
        stageCardapioPedido.initOwner(root.getScene().getWindow());
        stageCardapioPedido.setTitle("Adicionar item do cardápio");
        stageCardapioPedido.show();
    }

    @FXML
    void finalizar(ActionEvent event) {
        Platform.runLater(() -> {
            try {
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
                    semItems.setHeaderText("O pedido alterado está vazio!");
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
                    String comanda = null;
                    Double valorComanda = null;
                    Double valorDesconto = null;
                    Double total = null;
                    if (items.isEmpty()) {
                        items = null;
                    }
                    pedidoParaAlterar.setItems(items);
                    if (!textoComanda.getText().isBlank()) {
                        comanda = textoComanda.getText();
                    }
                    pedidoParaAlterar.setComanda(comanda);
                    if (!valorAdicional.getText().isBlank()) {
                        String valorSemVirgula = valorAdicional.getText().replace(",", ".");
                        valorComanda = Double.parseDouble(valorSemVirgula);
                    }
                    pedidoParaAlterar.setValorComanda(valorComanda);
                    if (!campoDesconto.getText().isBlank()) {
                        String decontoSemVirgula = campoDesconto.getText().replace(",", ".");
                        valorDesconto = Double.parseDouble(decontoSemVirgula);
                    }
                    pedidoParaAlterar.setDesconto(valorDesconto);

                    if (!items.isEmpty() && valorComanda == null) { // só itens na lista
                        if (valorDesconto != null) {
                            Double somaDosItens = items.stream().map(i -> i.getValorPorUnidade()).reduce(0.0, (a, b) -> a + b);
                            total = truncate(somaDosItens - valorDesconto);
                        } else {
                            Double somaDosItens = items.stream().map(i -> i.getValorPorUnidade()).reduce(0.0, (a, b) -> a + b);
                            somaDosItens = truncate(somaDosItens);
                            total = truncate(somaDosItens);
                        }
                    } else if (items.isEmpty() && valorComanda != null) { // só a comanda
                        if (valorDesconto != null) {
                            total = truncate(valorComanda - valorDesconto);
                        } else {
                            total = truncate(valorComanda);
                        }
                    } else { // os dois na lista
                        if (valorDesconto != null) {
                            Double somaDosItens = items.stream().map(i -> i.getValorPorUnidade()).reduce(0.0, (a, b) -> a + b);
                            total = truncate((somaDosItens + valorComanda) - valorDesconto);
                        } else {
                            Double somaDosItens = items.stream().map(i -> i.getValorPorUnidade()).reduce(0.0, (a, b) -> a + b);
                            total = truncate(somaDosItens + valorComanda);
                        }
                    }
                    pedidoParaAlterar.setTotal(total);
                    boolean alterou = PedidoDAO.atualizarPedido(pedidoParaAlterar);
                    if (alterou) {
                        mainController.pedidoAtualizado(pedidoParaAlterar);
                        mainController.getAlterarPedidoStage().close();
                        mainController.getListaDePedidos().getSelectionModel().clearSelection();
                        Alert pedidoAtualizado = new Alert(AlertType.INFORMATION);
                        pedidoAtualizado.setHeaderText("Pedido atualizado com sucesso");
                        pedidoAtualizado.setTitle("Aviso");
                        pedidoAtualizado.showAndWait();
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

    public void setMainController(TelaOpController controller) {
        if (controller != null) {
            mainController = controller;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            items = new ArrayList<>();
            if (pedidoParaAlterar.getItensDoCardapio() != null) {
                pedidoParaAlterar.getItensDoCardapio().forEach(i -> listaItensCardapio.getItems().add(i.getDescricaoItem()));
                items.addAll(pedidoParaAlterar.getItensDoCardapio());
                listaItensCardapio.refresh();
            }
            if (pedidoParaAlterar.getComanda() != null && !pedidoParaAlterar.getComanda().isBlank()) {
                textoComanda.setText(pedidoParaAlterar.getComanda());
            }
            if (pedidoParaAlterar.getValorComanda() != null) {
                String valor = Double.toString(pedidoParaAlterar.getValorComanda()).replace(".", ",");
                if (valor.charAt(valor.length() - 2) == ',') {
                    valor = valor + "0";
                }
                valorAdicional.setText(valor);
            }
            if (pedidoParaAlterar.getDesconto() != null) {
                String valorDesconto = Double.toString(pedidoParaAlterar.getDesconto()).replace(".", ",");
                if (valorDesconto.charAt(valorDesconto.length() - 2) == ',') {
                    valorDesconto = valorDesconto + "0";
                }
                campoDesconto.setText(valorDesconto);
            }
        });
    }

    public void setRoot(Parent esteRoot) {
        if (esteRoot != null) {
            this.root = esteRoot;
        }
    }

    public void setItem(CardapioDTO item) {
        if (item != null) {
            this.item = item;
        }
    }

    public void itemAdicionado() {
        if (item != null) {
            items.add(item);
            listaItensCardapio.getItems().addAll(item.getDescricaoItem());
        }
    }

    public double truncate(double value) {
        return Math.round(value * 100) / 100d;
    }

}
