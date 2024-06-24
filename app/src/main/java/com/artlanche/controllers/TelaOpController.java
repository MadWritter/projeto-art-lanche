package com.artlanche.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import com.artlanche.App;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.transaction.CaixaDAO;
import com.artlanche.model.transaction.PedidoDAO;
import com.artlanche.view.tools.Layout;

import javafx.application.Platform;
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

    private String campoDescontoTexto;

    @FXML
    private TextArea campoItensCardapio;

    @FXML
    private TextArea campoNumeroPedido;

    @FXML
    private Label campoTotal;

    private String campoTotalTexto;

    @FXML
    private Label campoValor;

    private String campoValorTexto;

    @FXML
    private Label labelUsuario;

    @FXML
    private ListView<String> listaDePedidos;

    private List<PedidoDTO> pedidos;

    private TelaPrincipalController telaPrincipalController;

    private TelaNovoPedidoController novoPedidoController;

    private TelaAlterarPedidoController alterarPedidoController;

    private TelaCardapioController telaCardapioController;

    private FinalizarCaixaController finalizarCaixaController;

    private Stage novoPedidoStage;

    private Stage alterarPedidoStage;

    private Stage finalizarCaixaStage;

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
    void alterarPedido(ActionEvent event) throws Exception {
        String selecionado = listaDePedidos.getSelectionModel().getSelectedItem();
        if (selecionado != null && !selecionado.isBlank()) {
            Long idPedido = Long.parseLong(selecionado);
            PedidoDTO pedidoParaAlterar = pedidos.stream().filter(p -> p.getId().equals(idPedido))
                    .collect(Collectors.toList()).get(0);
            FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaAlterarPedido.fxml"));
            Parent root = fxml.load();

            alterarPedidoController = fxml.getController();
            alterarPedidoController.setMainController(this);
            alterarPedidoController.pedidoParaAlterar(pedidoParaAlterar);
            alterarPedidoController.setRoot(root);

            alterarPedidoStage = new Stage();
            alterarPedidoStage.setScene(new Scene(root));
            alterarPedidoStage.setTitle("Alterar pedido #" + selecionado);
            alterarPedidoStage.initModality(Modality.WINDOW_MODAL);
            alterarPedidoStage.initOwner(esteRoot.getScene().getWindow());
            alterarPedidoStage.show();
        } else {
            Alert nenhumItemSelecionadoParaAlterar = new Alert(AlertType.ERROR);
            nenhumItemSelecionadoParaAlterar.setHeaderText("Selecione um item na lista primeiro");
            nenhumItemSelecionadoParaAlterar.setTitle("Aviso");
            nenhumItemSelecionadoParaAlterar.showAndWait();
        }
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
        stage.showAndWait();
    }

    @FXML
    void contato(ActionEvent event) throws Exception{
        Parent root = FXMLLoader.load(Layout.loader("Contato.fxml"));
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Contato");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(esteRoot.getScene().getWindow());
        stage.showAndWait();
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
    void fecharCaixa(ActionEvent event) throws Exception {
        List<PedidoDTO> listaConcluidos = PedidoDAO.getListaPedidosConcluidos(caixaId);
        List<PedidoDTO> listaNaoConcluidos = PedidoDAO.getListaPedidosNaoConcluidos(caixaId);
        if (listaConcluidos != null && !listaConcluidos.isEmpty() && listaNaoConcluidos == null) {
            FXMLLoader fxml = new FXMLLoader(Layout.loader("FinalizarCaixa.fxml"));
            Parent fecharCaixa = fxml.load();

            finalizarCaixaController = fxml.getController();
            finalizarCaixaController.setTelaOpController(this);
            finalizarCaixaController.setPedidosFinalizados(listaConcluidos);

            Stage finalizar = new Stage();
            finalizar.setScene(new Scene(fecharCaixa));
            finalizar.setTitle("Finalizar caixa");
            finalizar.showAndWait();
        } else {
            if (listaNaoConcluidos != null && !listaNaoConcluidos.isEmpty()) {
                Alert pedidosNãoFinalizados = new Alert(AlertType.ERROR);
                pedidosNãoFinalizados.setHeaderText("Finalize os pedidos efetuados primeiro!");
                pedidosNãoFinalizados.setTitle("Aviso");
                pedidosNãoFinalizados.showAndWait();
            } else {
                Alert semPedidosNoCaixa = new Alert(AlertType.CONFIRMATION);
                semPedidosNoCaixa.setHeaderText("Finalizar caixa sem nenhum pedido feito?");
                semPedidosNoCaixa.setTitle("Aviso");
                Optional<ButtonType> finalizar = semPedidosNoCaixa.showAndWait();
                if (finalizar.isPresent() && finalizar.get() == ButtonType.OK) {
                    boolean finalizou = CaixaDAO.fecharCaixa(caixaId, null, null, null,
                            telaPrincipalController.getUsuarioAtual().getNome(), null);
                    if (finalizou) {
                        Alert fecharPrograma = new Alert(AlertType.CONFIRMATION);
                        fecharPrograma.setHeaderText("Encerrar?");
                        fecharPrograma.setTitle("Aviso");
                        Optional<ButtonType> encerrar = fecharPrograma.showAndWait();
                        if (encerrar.isPresent() && encerrar.get() == ButtonType.OK) {
                            System.exit(0);
                        } else {
                            FXMLLoader fxml = new FXMLLoader(Layout.loader("TelaPrincipal.fxml"));
                            Parent voltar = fxml.load();

                            App.getTela().setScene(new Scene(voltar));
                            App.getTela().centerOnScreen();
                        }
                    }
                }
            }
        }
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
        String selecionado = listaDePedidos.getSelectionModel().getSelectedItem();
        if (selecionado != null && !selecionado.isBlank()) {
            Long id = Long.parseLong(selecionado);
            Alert alertaFechar = new Alert(AlertType.CONFIRMATION);
            alertaFechar.setHeaderText("Finalizar pedido #" + selecionado + "?");
            alertaFechar.setTitle("Aviso");
            Optional<ButtonType> escolha = alertaFechar.showAndWait();
            if (escolha.isPresent() && escolha.get() == ButtonType.OK) {
                PedidoDTO pedidoParaFinalizar = pedidos.stream().filter(p -> p.getId().equals(id))
                        .collect(Collectors.toList()).get(0);
                if (pedidoParaFinalizar != null) {
                    boolean finalizou = PedidoDAO.finalizarPedido(pedidoParaFinalizar);
                    if (finalizou) {
                        atualizou();
                        Alert finalizado = new Alert(AlertType.INFORMATION);
                        finalizado.setHeaderText("Pedido Finalizado!");
                        finalizado.setTitle("Aviso");
                        finalizado.showAndWait();
                    }
                }
            }
        }
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
            campoValorTexto = campoValor.getText();
            campoDescontoTexto = campoDesconto.getText();
            campoTotalTexto = campoTotal.getText();
            caixaId = telaPrincipalController.getCaixaId();
            atualizou();
            String usuarioAtual = telaPrincipalController.getUsuarioAtual().getNome();
            String textoLabel = labelUsuario.getText();
            labelUsuario.setText(textoLabel + " " + usuarioAtual);
            campoComanda.setEditable(false);
            campoItensCardapio.setEditable(false);
            campoNumeroPedido.setEditable(false);

        });
        listaDePedidos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String selecionado = listaDePedidos.getSelectionModel().getSelectedItem();
                if (selecionado != null && !selecionado.isBlank()) {
                    Long id = Long.parseLong(selecionado);
                    PedidoDTO pedido = pedidos.stream().filter(p -> p.getId().equals(id)).collect(Collectors.toList())
                            .get(0);
                    String valorFormatado = calcularValorEmStringView(pedido);
                    String descontoFormatado = calcularDescontoEmString(pedido);
                    String total = calcularTotal(pedido);

                    if (valorFormatado != null && valorFormatado.charAt(valorFormatado.length() - 2) == ',') {
                        valorFormatado = valorFormatado + "0";
                    }
                    if (descontoFormatado != null && descontoFormatado.charAt(descontoFormatado.length() - 2) == ',') {
                        descontoFormatado = descontoFormatado + "0";
                    }
                    if (total != null && total.charAt(total.length() - 2) == ',') {
                        total = total + "0";
                    }
                    if (valorFormatado != null) {
                        campoValor.setText(campoValorTexto + " R$ " + valorFormatado);
                    }
                    if (total != null) {
                        campoTotal.setText(campoTotalTexto + " R$ " + total);
                    }
                    if (descontoFormatado != null) {
                        campoDesconto.setText(campoDescontoTexto + " R$ " + descontoFormatado);
                    }
                    campoNumeroPedido.setText("#" + pedido.getId());
                    String itens = "";
                    if (pedido.getItensDoCardapio() != null) {
                        // Cria um mapa de contagem de descrições de itens
                        Map<String, Long> contagemItens = pedido.getItensDoCardapio().stream()
                                .collect(Collectors.groupingBy(i -> i.getDescricaoItem(), Collectors.counting()));

                        // Itera sobre os itens do cardápio
                        for (Map.Entry<String, Long> entry : contagemItens.entrySet()) {
                            String itemAtual = entry.getKey();
                            long ocorrências = entry.getValue();

                            String valor;
                            if (ocorrências > 1) {
                                valor = ocorrências + " " + itemAtual + ", ";
                            } else {
                                valor = itemAtual + ", ";
                            }

                            // Adiciona ao resultado final
                            itens += valor;
                        }

                        // Remove a última vírgula e espaço, se necessário
                        if (itens.endsWith(", ")) {
                            itens = itens.substring(0, itens.length() - 2);
                        }
                    }
                    if (itens != null) {
                        campoItensCardapio.setText(itens);
                    }
                    if (pedido.getComanda() != null) {
                        campoComanda.setText(pedido.getComanda());
                    }
                } else {
                    campoValor.setText(campoValorTexto);
                    campoTotal.setText(campoTotalTexto);
                    campoDesconto.setText(campoDescontoTexto);
                    campoNumeroPedido.clear();
                    campoItensCardapio.clear();
                    campoComanda.clear();
                }
            }

            private String calcularTotal(PedidoDTO pedido) {
                Double totalListaCardapio = null;
                Double valorComanda = null;
                Double desconto = null;
                if (pedido.getItensDoCardapio() != null) {
                    totalListaCardapio = pedido.getItensDoCardapio().stream().map(i -> i.getValorPorUnidade())
                            .reduce(0.0, (a, b) -> a + b);
                }
                if (pedido.getValorComanda() != null) {
                    valorComanda = pedido.getValorComanda();
                }
                if (pedido.getDesconto() != null) {
                    desconto = pedido.getDesconto();
                }
                if (totalListaCardapio != null && valorComanda != null) {
                    if (desconto != null) {
                        Double t1 = (totalListaCardapio + valorComanda) - desconto;
                        t1 = truncate(t1);
                        return Double.toString(t1).replace(".", ",");
                    } else {
                        Double t2 = totalListaCardapio + valorComanda;
                        t2 = truncate(t2);
                        return Double.toString(t2).replace(".", ",");
                    }
                } else if (totalListaCardapio != null && valorComanda == null) {
                    if (desconto != null) {
                        Double t3 = totalListaCardapio - desconto;
                        t3 = truncate(t3);
                        return Double.toString(t3).replace(".", ",");
                    } else {
                        Double t4 = totalListaCardapio;
                        t4 = truncate(t4);
                        return Double.toString(t4).replace(".", ",");
                    }
                } else {
                    if (desconto != null) {
                        Double t5 = valorComanda - desconto;
                        t5 = truncate(t5);
                        return Double.toString(t5).replace(".", ",");
                    } else {
                        Double t6 = valorComanda;
                        t6 = truncate(t6);
                        return Double.toString(t6).replace(".", ",");
                    }
                }
            }

            private String calcularDescontoEmString(PedidoDTO pedido) {
                if (pedido.getDesconto() != null) {
                    return Double.toString(pedido.getDesconto()).replace(".", ",");
                } else {
                    return null;
                }
            }

            private String calcularValorEmStringView(PedidoDTO pedido) {
                Double totalListaCardapio = null;
                Double valorComanda = null;

                if (pedido.getItensDoCardapio() != null) {
                    totalListaCardapio = pedido.getItensDoCardapio().stream().map(i -> i.getValorPorUnidade())
                            .reduce(0.0, (a, b) -> a + b);
                }
                if (pedido.getValorComanda() != null) {
                    valorComanda = pedido.getValorComanda();
                }

                if (totalListaCardapio != null && valorComanda != null) {
                    Double totalDouble = totalListaCardapio + valorComanda;
                    totalDouble = truncate(totalDouble);
                    return Double.toString(totalDouble).replace(".", ",");
                } else if (totalListaCardapio == null && valorComanda != null) {
                    return Double.toString(valorComanda).replace(".", ",");
                } else {
                    Double totalCardapio = truncate(totalListaCardapio);
                    return Double.toString(totalCardapio).replace(".", ",");
                }
            }

            public double truncate(double value) {
                return Math.round(value * 100) / 100d;
            }
        });
    }

    public void setMainController(TelaPrincipalController telaPrincipalController) {
        if (telaPrincipalController != null) {
            this.telaPrincipalController = telaPrincipalController;
        }
    }

    public void atualizou() {
        pedidos = PedidoDAO.getListaPedidosNaoConcluidos(caixaId);
        if (pedidos != null && !pedidos.isEmpty()) {
            listaDePedidos.getItems().clear();
            List<String> idPedidos = pedidos.stream().map(p -> p.getId()).map(id -> Long.toString(id))
                    .collect(Collectors.toList());
            listaDePedidos.getItems().addAll(idPedidos);
        } else {
            listaDePedidos.getSelectionModel().clearSelection();
            listaDePedidos.getItems().clear();
            listaDePedidos.refresh();
        }
    }

    public void pedidoAtualizado(PedidoDTO pedidoAtualizado) {
        Platform.runLater(() -> {
            PedidoDTO pedidoAntigo = pedidos.stream().filter(p -> p.getId().equals(pedidoAtualizado.getId()))
                    .collect(Collectors.toList()).get(0);
            pedidos.remove(pedidoAntigo);
            pedidos.add(pedidoAtualizado);
        });
    }
}
