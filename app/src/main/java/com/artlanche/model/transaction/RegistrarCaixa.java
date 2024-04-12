package com.artlanche.model.transaction;

import java.time.LocalDate;

import com.artlanche.JanelaCaixa;
import com.artlanche.model.database.Database;
import com.artlanche.model.entities.Caixa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class RegistrarCaixa {


    public static void novoCaixa(Double valorInicial, LocalDate dataAbertura, String nome) {
        // Gera um objeto caixa com os valores
        Caixa caixa = 
            new Caixa(valorInicial, 0, dataAbertura.getDayOfMonth(), dataAbertura.getMonthValue(), dataAbertura.getYear(), nome);

        // faz a inserção no banco
        try(EntityManager em = Database.getCaixaManager()) {
            em.getTransaction().begin();
            em.persist(caixa);
            em.getTransaction().commit();
            Alert alerta = new Alert(AlertType.INFORMATION);
            alerta.setTitle("Sucesso!");
            alerta.setHeaderText("Caixa criado com sucesso");
            alerta.showAndWait();
            JanelaCaixa.fecharJanelaCaixa();
        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro ao cadastrar o caixa no banco, tente novamente");
            alerta.showAndWait();
            throw new PersistenceException("Erro ao cadastrar na base de dados" + e.getMessage());
        }
    }
}
