package com.artlanche.model.entities;

import java.util.List;

import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.dtos.PedidoDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@Entity
@Table(name = "pedidos")
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "caixa_id", nullable = false)
    private Long caixaId;
    @Column(name = "itens_cardapio", nullable = true)
    private Long[] itensCardapioId;
    @Column(name = "comanda",nullable = true)
    private String textoComanda;
    @Column(name = "valor_comanda",nullable = true)
    private Double valorComanda;
    @Column(name = "desconto",nullable = true)
    private Double valorDesconto;
    @Column(name = "total")
    private Double total;
    @Column(name = "concluido", nullable = false)
    private Boolean concluido;

    public Pedido(PedidoDTO pedidoDTO) {
        this.caixaId = pedidoDTO.getCaixaId();
        if (pedidoDTO.getItensDoCardapio() == null) {
            this.itensCardapioId = null;
        } else {
            itensCardapioId = associarIDItemCardapio(pedidoDTO.getItensDoCardapio());
        }
        this.textoComanda = pedidoDTO.getComanda();
        this.valorComanda = pedidoDTO.getValorComanda();
        this.valorDesconto = pedidoDTO.getDesconto();
        this.total = pedidoDTO.getTotal();
        this.concluido = false;
    }

    public Long[] associarIDItemCardapio(List<CardapioDTO> itensDoCardapio) {
        if (itensDoCardapio != null) {
            Long[] array = new Long[itensDoCardapio.size()];
            int contador = 0;
            for(CardapioDTO item : itensDoCardapio) {
                long idAtual = item.getId();
                array[contador] = idAtual;
                contador++;
            }
            return array;
        } else {
            return null;
        }
    }

    public void setItensCardapioId(Long[] ids) {
        this.itensCardapioId = ids;
    }

    public void concluirPedido() {
        this.concluido = true;
    }

    public void setTextoComanda(String comanda) {
        this.textoComanda = comanda;
    }

    public void setValorComanda(Double valorComanda2) {
        this.valorComanda = valorComanda2;
    }

    public void setDesconto(Double desconto) {
        this.valorDesconto = desconto;
    }

    public void setTotal(Double total2) {
        this.total = total2;
    }
}
