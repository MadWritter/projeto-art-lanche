package com.artlanche.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.artlanche.App;
import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.dtos.PrecoCardapioDTO;
import com.artlanche.model.transaction.CaixaDAO;
import com.artlanche.model.transaction.CardapioDAO;
import com.artlanche.model.transaction.PrecoCardapioDAO;
import com.artlanche.view.tools.Layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FinalizarCaixaController {

    @FXML
    private TextField campoNome;

    @FXML
    private DatePicker dataFechamento;

    private List<PedidoDTO> pedidos;

    private TelaOpController telaOp;

    @FXML
    void cancelar(ActionEvent event) {
        telaOp.getFinalizarCaixaStage().close();
    }

    @FXML
    void finalizar(ActionEvent event) throws IOException {
        if (!campoNome.getText().isBlank() && dataFechamento.getValue() != null) {
            String descricao = campoNome.getText();
            Long caixaID = telaOp.getCaixaId();
            Double valorFinal;
            if (pedidos.size() > 1) {
                System.out.println("caiu no if");
                valorFinal = pedidos.stream().map(p -> p.getTotal()).reduce(0.0, (a, b) -> a + b);
            } else {
                valorFinal = pedidos.get(0).getTotal();
            }
            valorFinal = truncate(valorFinal);
            String usuarioFechamento = telaOp.getTelaPrincipalController().getUsuarioAtual().getNome();
            List<Long> ids = pedidos.stream().map(p -> p.getId()).collect(Collectors.toList());
            boolean fechou = CaixaDAO.fecharCaixa(caixaID, descricao, valorFinal, dataFechamento.getValue(), usuarioFechamento,
                    ids);
            if (fechou) {
                List<CardapioDTO> itensCardapio = CardapioDAO.getListaCardapio();
                if (itensCardapio != null && !itensCardapio.isEmpty()) {
                    for (CardapioDTO pedido : itensCardapio) {
                        PrecoCardapioDTO itemAtual = new PrecoCardapioDTO(pedido.getId(), pedido.getDescricaoItem(),
                                pedido.getValorPorUnidade(), dataFechamento.getValue());
                        PrecoCardapioDAO.cadastrarValorCardapio(itemAtual);
                    }
                }
                Alert finalizado = new Alert(AlertType.CONFIRMATION);
                finalizado.setHeaderText("Finalizado com sucesso, encerrar?");
                finalizado.setTitle("Aviso");
                Optional<ButtonType> escolha = finalizado.showAndWait();
                if (escolha.isPresent() && escolha.get() == ButtonType.OK) {
                    System.exit(0);
                } else {
                    telaOp.getFinalizarCaixaStage().close();
                    FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaPrincipal.fxml"));
                    Parent telap = fxml.load();

                    App.getTela().setScene(new Scene(telap));
                    App.getTela().centerOnScreen();
                }
            }
        } else {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setHeaderText("Preencha os dados corretamente");
            alerta.setTitle("Aviso");
            alerta.showAndWait();
        }
    }

    public void setTelaOpController(TelaOpController telaOpController) {
        if (telaOpController != null) {
            this.telaOp = telaOpController;
        }
    }

    public void setPedidosFinalizados(List<PedidoDTO> pedidos) {
        if (pedidos != null) {
            this.pedidos = pedidos;
        }
    }

    public double truncate(double value) {
        return Math.round(value * 100) / 100d;
    }
}
