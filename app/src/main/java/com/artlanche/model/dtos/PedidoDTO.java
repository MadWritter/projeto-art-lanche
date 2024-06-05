package com.artlanche.model.dtos;

import java.util.ArrayList;
import java.util.List;

import com.artlanche.model.entities.Cardapio;
import com.artlanche.model.entities.Pedido;
import com.artlanche.model.transaction.CardapioDAO;

import lombok.Getter;

@Getter
public class PedidoDTO {
    
    Long id;
    Long caixaId;
    List<Cardapio> itensDoCardapio;
    String comanda;
    Double valorComanda;
    Double desconto;

    public PedidoDTO(Long caixaId,
        List<Cardapio> itensDoCardapio, String comanda, Double valorComanda, Double desconto) {
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
        this.itensDoCardapio = associarListaCardapio(pedido.getItensCardapioId());
        this.comanda = pedido.getTextoComanda();
        this.valorComanda = pedido.getValorComanda();
        this.desconto = pedido.getValorDesconto();
    }

    private List<Cardapio> associarListaCardapio(Long[] itensCardapioId) {
        List<Cardapio> lista = new ArrayList<>();
        for(int i = 0; i < itensCardapioId.length; i++) {
            long indice = itensCardapioId[i];
            Cardapio cardapio = CardapioDAO.getCardapioById(indice);
            lista.add(cardapio);
        }
        return lista;
    }
}
