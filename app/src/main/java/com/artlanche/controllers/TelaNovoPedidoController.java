package com.artlanche.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.artlanche.view.tools.Layout;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    @FXML
    private TextArea textoComanda;

    @FXML
    private TextField valorAdicional;

    private TelaOpController controllerPai;

    private TelaCardapioPedidoController cardapioPedidoController;

    private Stage stageCardapioPedidoController;

    private Parent esteRoot;

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
        
    }

    @FXML
    void removerItem(ActionEvent event) {
        
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }

    public void setMainController(TelaOpController telaOpController) {
        if (telaOpController != null) {
            controllerPai = telaOpController;
        }
    }
}
