package com.artlanche.model.entities;

import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Classe que representa o caixa do negócio, contendo os valores e datas
 * @since 1.0
 * @author Jean Maciel
 */
@Getter
@NoArgsConstructor
@Entity
@Table(name = "caixa")
public class Caixa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "valor_inicial")
    private double valorInicial;
    @Column(name = "valor_final")
    private double valorFinal;
    @Column(name = "data_abertura")
    private Date dataAbertura;
    @Column(name = "data_fechamento")
    private Date dataFechamento;
    @Column(name = "opAbertura")
    private String opAbertura;
    @Column(name = "opFechamento")
    private String opFechamento;
    private double lucro;
    private boolean aberto;

    

    /**
     * Construtor que deve ser usado para gerar um caixa
     * @param valorInicial - valor inicial do caixa
     * @param valorFinal - valor do caixa no final do dia
     * @param dia - dia de aberura
     * @param mes - mes de abertura
     * @param ano - ano de abertura
     */
    public Caixa(double valorInicial, double valorFinal, int dia, int mes, int ano, String opAbertura) {
        setValorInicial(valorInicial);
        setValorFinal(valorFinal);
        setLucro(0.0);
        setAberto(true);
        setDataAbertura(dia, mes, ano);
        setOpAbertura(opAbertura);
    }

    public void setValorInicial(double valorInicial) {
        this.valorInicial = valorInicial;
    }
    public void setValorFinal(double valorFinal) {
        this.valorFinal = valorFinal;
    }
    public void setLucro(double lucro) {
        this.lucro = lucro;
    }
    public void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public void setOpAbertura(String nome) {
        if (nome != null) {
            this.opAbertura = nome;
        }
    }

    public void setOpFechamento(String nome) {
        if (nome != null) {
            this.opFechamento = nome;
        }
    }
    
    /**
     * Configura e valida a data de abertura do caixa
     * @param diaInformado - dia informado no construtor
     * @param mesInformado - mês informado no construtor
     * @param anoInformado - ano informado no construtor
     * @throws IllegalStateException se a data informada for abaixo da data do sistema
     */
    public void setDataAbertura(int diaInformado, int mesInformado, int anoInformado) throws IllegalStateException {
        java.util.Date dataAtual = new java.util.Date(); // data atual

        // Data informada no método
        LocalDate dataInformada = LocalDate.of(anoInformado, mesInformado, diaInformado);
        // Data do sistema em formato LocalDate, usa um Date para isso
        LocalDate dataAtualLocalDate = dataAtual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // compara se a data informada é antes da data atual
        if (dataInformada.isBefore(dataAtualLocalDate)) {
            throw new IllegalStateException("A Data informada é anterior ao dia atual");
        } else {
            this.dataAbertura = Date.valueOf(dataInformada); // caso contrário, associa a data informada
        }

    }

    /**
     * Faz o fechamento do caixa, caso esteja aberto
     */
    public void fecharCaixa() {
        if (aberto) {
            var dataAtual = new java.util.Date();
            LocalDate dataAtualLocalDate = dataAtual.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            this.dataFechamento = Date.valueOf(dataAtualLocalDate);
        }
    }
}
