package com.artlanche.model.transaction;

import java.time.LocalDate;
import java.util.List;

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
    public static Long novoCaixa(Double valorInicial, LocalDate dataAbertura, String nome) {
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
            return caixa.getId();

        } catch (PersistenceException e) {
            return null;
        }
    }

    /**
     * Feito para verificar na inicialização da tela principal
     * se o caixa está aberto
     * @return um Caixa(caso exista) ou null(caso não exista
     */
    public static Caixa verificarSeCaixaAberto() {
        try(EntityManager em = Database.getCaixaManager()) {
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

    public static Long getCaixaAbertoId() {
        try(EntityManager em = Database.getCaixaManager()) {
            var query = em.createQuery("SELECT c.id FROM Caixa c WHERE c.aberto=true", Long.class);
            return query.getSingleResult();
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean fecharCaixa(Long caixaID, String descricao,Double valorFinal, LocalDate dataFechamento,
            String usuarioFechamento, List<Long> ids) {
        try(EntityManager em = Database.getCaixaManager()) {
            em.getTransaction().begin();
            Caixa c = em.find(Caixa.class, caixaID);
            if (c != null) {
                c.setDescricao(descricao);
                if (valorFinal != null) {
                    c.setValorFinal(valorFinal + c.getValorInicial());
                } else {
                    c.setValorFinal(0.0);
                }
                c.setDataFechamento(dataFechamento);
                c.setOpFechamento(usuarioFechamento);
                c.setIdPedidos(ids);
                if (valorFinal != null) {
                    c.setLucro(c.getValorFinal() - c.getValorInicial());
                } else {
                    c.setLucro(0.0);
                }
                c.setAberto(false);
                em.flush();
                em.getTransaction().commit();
                return true;
            } else {
                throw new PersistenceException();
            } 
        } catch(PersistenceException e) {
            return false;
        }
    }
}
