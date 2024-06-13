package com.artlanche.model.dtos;


import java.sql.Date;
import java.time.LocalDate;

import com.artlanche.model.entities.PrecoCardapio;

import lombok.Getter;

@Getter
public class PrecoCardapioDTO {
    
    private Long id;
    private Long cardapioId;
    private String descricaoItem;
    private Double valorPorUnidade;
    private LocalDate dataDoValor;

    public PrecoCardapioDTO(PrecoCardapio item) {
        this.id = item.getId();
        this.cardapioId = item.getCardapioId();
        this.descricaoItem = item.getDescricaoItem();
        this.valorPorUnidade = item.getValorPorUnidade();
        this.dataDoValor = ajustarData(item.getDataDoValor());
    }

    private LocalDate ajustarData(Date dataDoValor2) {
        if (dataDoValor2 != null) {
            return dataDoValor2.toLocalDate();
        } else {
            return null;
        }
    }

    public PrecoCardapioDTO(Long cardapioId, String descricaoItem, Double valorPorUnidade, LocalDate dataDoValor) {
        this.cardapioId = cardapioId;
        this.descricaoItem = descricaoItem;
        this.valorPorUnidade = valorPorUnidade;
        this.dataDoValor = dataDoValor;
    }
}
