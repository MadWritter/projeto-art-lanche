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

    public PedidoDTO(Long caixaId,
        List<CardapioDTO> itensDoCardapio, String comanda, Double valorComanda, Double desconto) {
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
    }

    public PedidoDTO(Pedido pedido) {
        this.id = pedido.getId();
        this.caixaId = pedido.getCaixaId();
        this.itensDoCardapio = associarListaCardapio(pedido.getItensCardapioId());
        this.comanda = pedido.getTextoComanda();
        this.valorComanda = pedido.getValorComanda();
        this.desconto = pedido.getValorDesconto();
    }

    private List<CardapioDTO> associarListaCardapio(Long[] itensCardapioId) {
        List<CardapioDTO> lista = new ArrayList<>();
        for(int i = 0; i < itensCardapioId.length; i++) {
            long indice = itensCardapioId[i];
            CardapioDTO cardapio = CardapioDAO.getCardapioById(indice);
            lista.add(cardapio);
        }
        return lista;
    }
}
