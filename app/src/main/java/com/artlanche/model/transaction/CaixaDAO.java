package com.artlanche.model.transaction;

import java.time.LocalDate;
import java.util.List;

import com.artlanche.SegundaJanela;
import com.artlanche.model.entities.Caixa;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
/**
 * Classe que representa os transações de caixa
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class CaixaDAO {


    /**
     * Faz o registro de um novo caixa
     * @param valorInicial - o valor do caixa para passagem de troco
     * @param dataAbertura - data de abertura do caixa
     * @param nome - nome do operador/usuário que abriu o caixa
     */
    public static void novoCaixa(Double valorInicial, LocalDate dataAbertura, String nome) {
        // Gera uma entidade caixa com os valores
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
            SegundaJanela.fechar();

        } catch (PersistenceException e) {

            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro ao cadastrar o caixa no banco, tente novamente");
            alerta.showAndWait();
            throw new PersistenceException("Erro ao cadastrar na base de dados" + e.getMessage());

        }
    }

    /**
     * Feito para verificar na inicialização da tela principal
     * se o caixa está aberto
     * @return um Caixa(caso exista) ou null(caso não exista
     */
    public static Caixa verificarSeCaixaAberto() {
        try(EntityManager em = Database.getCaixaManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Caixa c WHERE c.aberto=true", Caixa.class);

            List<Caixa> resultList = query.getResultList();
            if (!resultList.isEmpty()) {
                return resultList.get(0);
            } else {
                return null;
            }
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
