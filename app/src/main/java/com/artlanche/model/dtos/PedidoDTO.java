package com.artlanche.model.dtos;

import java.util.ArrayList;
import java.util.List;

import com.artlanche.model.entities.Pedido;
import com.artlanche.model.transaction.CardapioDAO;

import lombok.Getter;

@Getter
public class PedidoDTO {
    
    private Long id;
    private Long caixaId;
    private List<CardapioDTO> itensDoCardapio;
    private String comanda;
    private Double valorComanda;
    private Double desconto;
    private Double total;

    public PedidoDTO(Long caixaId,
        List<CardapioDTO> itensDoCardapio, String comanda, Double valorComanda, Double desconto, Double total) {
            this.caixaId = caixaId;
            if (itensDoCardapio != null) {
                this.itensDoCardapio = itensDoCardapio;
            }
            if (comanda != null) {
                this.comanda = comanda;
            }
            if (valorComanda != null) {
                this.valorComanda = valorComanda;
            }
            if (desconto != null) {
                this.desconto = desconto;
            }
            if (total != null) {
                this.total = total;

            }
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.caixaId = pedido.getCaixaId();
        this.itensDoCardapio = associarListaCardapio(pedido.getItensCardapioId());
        this.comanda = pedido.getTextoComanda();
        this.valorComanda = pedido.getValorComanda();
        this.desconto = pedido.getValorDesconto();
        this.total = pedido.getTotal();
    }

    private List<CardapioDTO> associarListaCardapio(Long[] itensCardapioId) {
        if (itensCardapioId != null) {
            List<CardapioDTO> lista = new ArrayList<>();
            for(int i = 0; i < itensCardapioId.length; i++) {
                long indice = itensCardapioId[i];
                CardapioDTO cardapio = CardapioDAO.getCardapioById(indice);
                lista.add(cardapio);
            }
            return lista;
        } else {
            return null;
        }
    }

    public void setItems(List<CardapioDTO> items) {
        this.itensDoCardapio = items;
    }

    public void setComanda(String text) {
        this.comanda = text;
    }

    public void setValorComanda(Double valorComanda2) {
        this.valorComanda = valorComanda2;
    }

    public void setDesconto(Double valorDesconto) {
        this.desconto = valorDesconto;
    }

    public void setTotal(Double total2) {
        this.total = total2;
    }
}
