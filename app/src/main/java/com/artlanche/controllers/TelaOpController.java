package com.artlanche.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.artlanche.SegundaJanela;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.entities.Pedido;
import com.artlanche.model.transaction.CaixaDAO;
import com.artlanche.model.transaction.PedidoDAO;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
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

    public static List<PedidoDTO> objetosPedidos;

    public int tamanhoDosPedidos;

    public static Long caixaId;

    @FXML
    void alterarPedido(ActionEvent event) {

    }

    @FXML
    void cardapio(ActionEvent event) throws Exception {
        new SegundaJanela<TelaCardapioController>("TelaCardapio.fxml", "Cardápio");
    }

    @FXML
    void contato(ActionEvent event) {

    }

    @FXML
    void excluirPedido(ActionEvent event) {

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
        SegundaJanela<TelaNovoPedidoController> segundaJanela = new SegundaJanela<>("TelaNovoPedido.fxml",
                "Novo Pedido");
        var controller = segundaJanela.getController();
        controller.getCampoDesconto().clear();
        controller.getListaItensCardapio().getItems().clear();
        controller.getTextoComanda().clear();
        controller.getValorAdicional().clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        caixaId = CaixaDAO.getCaixaAbertoId();
        objetosPedidos = new ArrayList<>();
        objetosPedidos.clear();
        listaDePedidos.getItems().clear();
        List<Pedido> pedidosConsultadosFirst = PedidoDAO.getListaPedidosNaoConcluidos(caixaId);
        if (pedidosConsultadosFirst != null && !pedidosConsultadosFirst.isEmpty()) {
            for (Pedido p : pedidosConsultadosFirst) {
                PedidoDTO dtoAtual = new PedidoDTO(p);
                objetosPedidos.add(dtoAtual);
                String idEmString = Long.toString(dtoAtual.getId());
                listaDePedidos.getItems().addAll(idEmString);
                listaDePedidos.refresh();
                tamanhoDosPedidos = objetosPedidos.size();
            }
        }
        tamanhoDosPedidos = objetosPedidos.size();
        String usuario = TelaPrincipalController.getUsuarioAtual().getNome();
        labelUsuario.setText(labelUsuario.getText() + " " + usuario);
        campoNumeroPedido.setEditable(false);
        campoItensCardapio.setEditable(false);
        campoComanda.setEditable(false);

        Thread pedidos = new Thread(() -> {
            while (true) {
                if (objetosPedidos.size() != tamanhoDosPedidos) {
                    objetosPedidos.clear();
                    Platform.runLater(() -> {
                        listaDePedidos.getItems().clear();
                    });
                    List<Pedido> pedidosConsultados = PedidoDAO.getListaPedidosNaoConcluidos(caixaId);
                    if (pedidosConsultados != null && !pedidosConsultados.isEmpty()) {
                        for (Pedido p : pedidosConsultados) {
                            PedidoDTO dtoAtual = new PedidoDTO(p);
                            objetosPedidos.add(dtoAtual);
                            String idEmString = Long.toString(dtoAtual.getId());
                            Platform.runLater(() -> {
                                listaDePedidos.getItems().addAll(idEmString);
                                listaDePedidos.refresh();
                            });
                            tamanhoDosPedidos = objetosPedidos.size();
                        }
                    }
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
        });
        pedidos.setDaemon(true);
        pedidos.start();
    }

}
