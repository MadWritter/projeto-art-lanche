package com.artlanche.model.dtos;

import com.artlanche.model.entities.Cardapio;

import lombok.Getter;

@Getter
public class CardapioDTO {

    private Long id;
    private String descricaoItem;
    private double valorPorUnidade;

    public CardapioDTO(Cardapio c) {
        if (c != null) {
            this.id = c.getId();
            this.descricaoItem = c.getDescricaoItem();
            this.valorPorUnidade = c.getValorPorUnidade();
        }
    }

    public CardapioDTO(String descricaoItem, double valorPorUnidade) {
        if (descricaoItem != null) {
            this.descricaoItem = descricaoItem;
        }
        this.valorPorUnidade = valorPorUnidade;
    }
    
}
