package com.artlanche.model.entities;

import com.artlanche.model.dtos.CardapioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "cardapio")
public class Cardapio {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "descricao_item ",length = 200, nullable = false)
    private String descricaoItem;
    @Column(name = "valor_por_unidade", nullable = false)
    private double valorPorUnidade;
    @Column(name = "ativo", nullable = false)
    private boolean ativo;

    public Cardapio(CardapioDTO item) {
        if (item != null) {
            this.descricaoItem = item.getDescricaoItem();
        }
        this.valorPorUnidade = item.getValorPorUnidade();
        this.ativo = true;
    }

    public void setDescricao(String descricao) {
        if (descricao != null) {
            this.descricaoItem = descricao;
        }
    }

    public void setValorPorUnidade(double valor) {
        this.valorPorUnidade = valor;
    }

    public void ativarItem() {
        this.ativo = true;
    }

    public void desativarItem() {
        this.ativo = false;
    }
}
