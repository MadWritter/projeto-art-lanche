package com.artlanche.model.entities;

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

    public Cardapio(String descricaoItem) {
        if (descricaoItem != null) {
            this.descricaoItem = descricaoItem;
        }
    }

    public void setDescricao(String descricao) {
        if (descricao != null) {
            descricaoItem = descricao;
        }
    }
}
