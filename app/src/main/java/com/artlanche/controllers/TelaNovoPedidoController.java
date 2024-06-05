package com.artlanche.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.artlanche.SegundaJanela;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.entities.Cardapio;
import com.artlanche.model.transaction.PedidoDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import lombok.Getter;

@Getter
public class TelaNovoPedidoController implements Initializable {

    private SegundaJanela<?> segundaJanela;

    @FXML
    private TextField campoDesconto;

    @FXML
    private ListView<String> listaItensCardapio;

    private List<Cardapio> listaDeItens;

    @FXML
    private TextArea textoComanda;

    @FXML
    private TextField valorAdicional;

    public static boolean atualizou;

    @FXML
    void adicionarItem(ActionEvent event) throws Exception {
        segundaJanela = new SegundaJanela<TelaCardapioPedidoController>("TelaCardapioPedido.fxml", "Cardápio");
    }

    @FXML
    void finalizar(ActionEvent event) {
        boolean semItensNemComanda = listaItensCardapio.getItems().isEmpty() && textoComanda.getText().isBlank();
        boolean comandaSemValorAdicional = !textoComanda.getText().isBlank() && valorAdicional.getText().isBlank();

        if (semItensNemComanda) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Por favor, preencha os itens primeiro");
            alerta.showAndWait();
        } else if (comandaSemValorAdicional) {
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Aviso");
            alerta.setHeaderText("Preencha o valor da comanda");
            alerta.showAndWait();
        } else {
            String comanda = null;
            String textoValorAdicional = null;
            String desconto = null;
            Double valorComanda = null;
            Double valorDesconto = null;

            try {
                if (!textoComanda.getText().isBlank()) {
                    comanda = textoComanda.getText();
                }

                if (!valorAdicional.getText().isBlank()) {
                    if (valorAdicional.getText().contains(".")) {
                        throw new NumberFormatException();
                    }
                    textoValorAdicional = valorAdicional.getText().replace(',', '.');
                    valorComanda = Double.parseDouble(textoValorAdicional);
                }

                if (!campoDesconto.getText().isBlank()) {
                    if (campoDesconto.getText().contains(".")) {
                        throw new NumberFormatException();
                    }
                    desconto = campoDesconto.getText().replace(',', '.');
                    valorDesconto = Double.parseDouble(desconto);
                }

                // Criação do PedidoDTO e chamada do método cadastrarNovoPedido
                PedidoDTO pedido = new PedidoDTO(TelaOpController.caixaId,listaDeItens, comanda, valorComanda, valorDesconto);
                boolean cadastrou = PedidoDAO.cadastrarNovoPedido(pedido);
                if (cadastrou) {
                    Alert alerta = new Alert(AlertType.INFORMATION);
                    alerta.setTitle("Aviso");
                    alerta.setHeaderText("Pedido Cadastrado com sucesso!");
                    alerta.showAndWait();
                    TelaOpController.objetosPedidos.add(pedido);
                } else {
                    Alert alerta = new Alert(AlertType.ERROR);
                    alerta.setTitle("Aviso");
                    alerta.setHeaderText("Erro ao cadastrar o pedido, tente novamente");
                    alerta.showAndWait();
                }

            } catch (NumberFormatException e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Aviso");
                alerta.setHeaderText("Corrija os campos de valores!");
                alerta.showAndWait();
            }
        }
    }

    @FXML
    void removerItem(ActionEvent event) {
        String selecionado = listaItensCardapio.getSelectionModel().getSelectedItem();
        if (selecionado != null && !selecionado.isBlank()) {
            Alert alerta = new Alert(AlertType.CONFIRMATION);
            alerta.setHeaderText("Tem certeza que quer remover este item?");
            alerta.setTitle("Aviso");
            Optional<ButtonType> escolha = alerta.showAndWait();

            if (escolha.isPresent() && escolha.get() == ButtonType.OK) {
                TelaCardapioPedidoController.item = null;
                listaItensCardapio.getItems().remove(selecionado);
                listaItensCardapio.refresh();
            }
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Selecione um item primeiro");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listaDeItens = new ArrayList<>();
        Thread thread = new Thread(() -> {
            while (true) {
                if (atualizou) {
                    Cardapio item = TelaCardapioPedidoController.item;
                    String desc = item.getDescricaoItem();
                    listaItensCardapio.getItems().add(desc);
                    listaDeItens.add(item);
                    atualizou = false;
                }

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException();
                }
            }
        });
        thread.start();
    }

}
