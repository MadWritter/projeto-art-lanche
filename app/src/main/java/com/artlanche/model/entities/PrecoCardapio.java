package com.artlanche.model.entities;

import java.sql.Date;
import java.time.LocalDate;

import com.artlanche.model.dtos.PrecoCardapioDTO;

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
@Table(name = "preco_cardapio")
public class PrecoCardapio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cardapio_id", nullable = false)
    private Long cardapioId;
    @Column(name = "descricao_item", nullable = false)
    private String descricaoItem;
    @Column(name = "valor_por_unidade", nullable = false)
    private Double valorPorUnidade;
    @Column(name = "data_do_valor", nullable = false)
    private Date dataDoValor;

    public PrecoCardapio(Long cardapioId,String descricaoItem, Double valorPorUnidade, LocalDate dataDoValor) {
        this.cardapioId = cardapioId;
        this.descricaoItem = descricaoItem;
        this.valorPorUnidade = valorPorUnidade;
        this.dataDoValor = ajustarData(dataDoValor);
    }

    public PrecoCardapio(PrecoCardapioDTO dto) {
        this.cardapioId = dto.getCardapioId();
        this.descricaoItem = dto.getDescricaoItem();
        this.valorPorUnidade = dto.getValorPorUnidade();
        this.dataDoValor = ajustarData(dto.getDataDoValor());
    }

    private Date ajustarData(LocalDate dataDoValor2) {
        if (dataDoValor2 != null) {
            LocalDate dataFormatada = LocalDate.of(dataDoValor2.getYear(), dataDoValor2.getMonthValue(), dataDoValor2.getDayOfMonth());
            return Date.valueOf(dataFormatada);
        } else {
            return null;
        }
    }

}
