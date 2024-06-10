package com.artlanche.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.artlanche.App;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.transaction.CaixaDAO;
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
            Double valorFinal = pedidos.stream().map(p -> p.getTotal()).reduce(0.0, (a, b) -> a + b);
            valorFinal = truncate(valorFinal);
            LocalDate dataFechamento = this.dataFechamento.getValue();
            String usuarioFechamento = telaOp.getTelaPrincipalController().getUsuarioAtual().getNome();
            List<Long> ids = pedidos.stream().map(p -> p.getId()).collect(Collectors.toList());
            boolean fechou = CaixaDAO.fecharCaixa(caixaID, descricao, valorFinal, dataFechamento, usuarioFechamento,
                    ids);
            if (fechou) {
                // TODO adicionar os ids e os valores do dia para uma tabela de controle
                Alert finalizado = new Alert(AlertType.CONFIRMATION);
                finalizado.setHeaderText("Finalizado com sucesso, encerrar?");
                finalizado.setTitle("Aviso");
                Optional<ButtonType> escolha = finalizado.showAndWait();
                if (escolha.isPresent() && escolha.get() == ButtonType.OK) {
                    System.exit(0);
                } else {
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
