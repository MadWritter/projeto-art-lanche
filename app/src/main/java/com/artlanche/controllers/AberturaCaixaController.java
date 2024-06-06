package com.artlanche.controllers;

import java.time.LocalDate;

import com.artlanche.model.transaction.CaixaDAO;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

/**
 * Classe controller dos eventos de abertura do caixa
 * @since 1.0
 * @author Jean Maciel
 */
public class AberturaCaixaController {

    @FXML
    private DatePicker DataAbertura;

    @FXML
    private TextField campoValorInicial;

    public TelaPrincipalController telaPrincipal;

    @FXML
    void cancelarAbertura(ActionEvent event) {
        
    }

    @FXML
    void confirmarAbertura(ActionEvent event) {
        if (campoValorInicial != null && DataAbertura != null) {
            Double valorInicial;
            LocalDate dataAbertura;
            try {

                valorInicial = Double.parseDouble(campoValorInicial.getText().replace(",", "."));
                dataAbertura = DataAbertura.getValue();

                Long id = CaixaDAO.novoCaixa(valorInicial, dataAbertura, telaPrincipal.getUsuarioAtual().getNome());
                if (id != null) {
                    enviarId(id);
                    telaPrincipal.getStageNovoCaixa().close();
                } else {
                    throw new NullPointerException();
                }
            } catch (NumberFormatException e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("O valor do caixa inserido está incorreto");
                alerta.showAndWait();
            } catch (NullPointerException e) {
                Alert alerta = new Alert(AlertType.ERROR);
                alerta.setTitle("Erro");
                alerta.setHeaderText("Não foi possível criar o caixa no banco, tente novamente!");
                alerta.showAndWait();
            }
        }
    }

    public void setMainController(TelaPrincipalController telaPrincipalController) {
        if (telaPrincipalController != null) {
            this.telaPrincipal = telaPrincipalController;
        }
    }

    public void enviarId(Long id) {
        if (telaPrincipal != null) {
            telaPrincipal.idRecebido(id);
        }
    }

}
