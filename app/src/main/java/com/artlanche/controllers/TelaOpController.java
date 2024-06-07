package com.artlanche.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.transaction.PedidoDAO;
import com.artlanche.view.tools.Layout;

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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class TelaOpController implements Initializable {

    @FXML
    private TextArea campoComanda;

    @FXML
    private Label campoDesconto;

    @FXML
    private TextArea campoItensCardapio;

    @FXML
    private TextArea campoNumeroPedido;

    @FXML
    private Label campoTotal;

    @FXML
    private Label campoValor;

    @FXML
    private Label labelUsuario;

    @FXML
    private ListView<String> listaDePedidos;

    private List<PedidoDTO> pedidos;

    private TelaPrincipalController telaPrincipalController;

    private TelaNovoPedidoController novoPedidoController;

    private Stage novoPedidoStage;

    private TelaCardapioController telaCardapioController;

    private Parent esteRoot;

    private Long caixaId;

    public void setRoot(Parent root) {
        if (root != null) {
            esteRoot = root;
        }
    }

    public List<PedidoDTO> getPedidos() {
        return pedidos;
    }

    @FXML
    void alterarPedido(ActionEvent event) {

    }

    @FXML
    void cardapio(ActionEvent event) throws Exception {
        FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaCardapio.fxml"));
        Parent root = fxml.load();

        telaCardapioController = fxml.getController();
        telaCardapioController.setParent(root);

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Cardápio");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(esteRoot.getScene().getWindow());
        stage.show();
    }

    @FXML
    void contato(ActionEvent event) {

    }

    @FXML
    void excluirPedido(ActionEvent event) {
        String itemSelecionado = listaDePedidos.getSelectionModel().getSelectedItem();
        if (itemSelecionado != null) {
            Alert alertaExclusao = new Alert(AlertType.CONFIRMATION);
            alertaExclusao.setHeaderText("Tem certeza que deseja remover o pedido " + itemSelecionado + "?");
            alertaExclusao.setTitle("Aviso");
            Optional<ButtonType> decisao = alertaExclusao.showAndWait();
            if (decisao.isPresent() && decisao.get() == ButtonType.OK) {
                Long idSelecionado = Long.parseLong(itemSelecionado);
                PedidoDTO pedido = pedidos.stream().filter(p -> p.getId().equals(idSelecionado))
                        .collect(Collectors.toList()).get(0);
                boolean removeuDaListaVisual = pedidos.remove(pedido);
                boolean removeuDoBanco = PedidoDAO.removerPedido(pedido);
                if (removeuDaListaVisual && removeuDoBanco) {
                    atualizou();
                    Alert removidos = new Alert(AlertType.INFORMATION);
                    removidos.setHeaderText("Pedido removido!");
                    removidos.setTitle("Aviso");
                    removidos.showAndWait();
                }
            }
        } else {
            Alert semSelecionar = new Alert(AlertType.ERROR);
            semSelecionar.setHeaderText("Selecione um item da lista primeiro");
            semSelecionar.setTitle("Aviso");
            semSelecionar.showAndWait();
        }
    }

    @FXML
    void fecharCaixa(ActionEvent event) {

    }

    @FXML
    void fecharPrograma(ActionEvent event) {
        Alert alertaSair = new Alert(AlertType.CONFIRMATION);
        alertaSair.setTitle("Aviso");
        alertaSair.setHeaderText("Tem certeza que quer encerrar?");

        alertaSair.showAndWait().ifPresent(resposta -> {
            if (resposta == ButtonType.OK) {
                System.exit(0);
            }
        });
    }

    @FXML
    void finalizarPedido(ActionEvent event) {

    }

    @FXML
    void novoPedido(ActionEvent event) throws Exception {
        FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaNovoPedido.fxml"));
        Parent root = fxml.load();
        novoPedidoController = fxml.getController();
        novoPedidoController.setRoot(root);
        novoPedidoController.setMainController(this);

        novoPedidoStage = new Stage();
        novoPedidoStage.setScene(new Scene(root));
        novoPedidoStage.setTitle("Novo Pedido");
        novoPedidoStage.initModality(Modality.WINDOW_MODAL);
        novoPedidoStage.initOwner(esteRoot.getScene().getWindow());
        novoPedidoStage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Platform.runLater(() -> {
            pedidos = new ArrayList<>();
            atualizou();
            caixaId = telaPrincipalController.getCaixaId();
            String usuarioAtual = telaPrincipalController.getUsuarioAtual().getNome();
            String textoLabel = labelUsuario.getText();
            labelUsuario.setText(textoLabel + " " + usuarioAtual);
            campoComanda.setEditable(false);
            campoItensCardapio.setEditable(false);
            campoNumeroPedido.setEditable(false);
        });
    }

    public void setMainController(TelaPrincipalController telaPrincipalController) {
        if (telaPrincipalController != null) {
            this.telaPrincipalController = telaPrincipalController;
        }
    }

    public void atualizou() {
        Platform.runLater(() -> {
            pedidos = PedidoDAO.getListaPedidosNaoConcluidos(caixaId);
            if (pedidos != null) {
                listaDePedidos.getItems().clear();
                List<String> idPedidos = pedidos.stream().map(p -> p.getId()).map(id -> Long.toString(id))
                        .collect(Collectors.toList());
                listaDePedidos.getItems().addAll(idPedidos);
            } else {
                listaDePedidos.getSelectionModel().clearSelection();
                listaDePedidos.getItems().clear();
                listaDePedidos.refresh();
            }
        });
    }

}
