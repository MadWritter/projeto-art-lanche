package com.artlanche.model.transaction;

import java.util.List;

import com.artlanche.model.entities.Cardapio;

import jakarta.persistence.EntityManager;

public class CardapioDAO {

    public static List<String> getListaCardapio() {
        try(EntityManager em = Database.getCardapioManager()) {
            var query = em.createQuery("SELECT c.descricaoItem FROM Cardapio c", String.class);
            return query.getResultList();
        } catch(Exception e) {
            return null;
        }
    }
    
    public static boolean adicionarItem(String nome) {
        Cardapio item = null;
        if (nome != null) {
            item = new Cardapio(nome);
        } else {
            throw new NullPointerException("O nome passado como argumento para o item é nulo");
        }

        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.descricaoItem=:nome", Cardapio.class);
            query.setParameter("nome", nome);
            if (query.getResultList().isEmpty()) {
                em.persist(item);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            throw e;
        }
    }

    public static boolean alterarItem(String nomeAntigo, String nomeNovo) {
        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.descricaoItem=:nomeAtual", Cardapio.class);
            query.setParameter("nomeAtual", nomeAntigo);
            Cardapio item = query.getSingleResult();

            if (item != null) {
                item.setDescricao(nomeNovo);
                em.flush();
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean excluirItem(String nome) {
        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.descricaoItem=:nome", Cardapio.class);
            query.setParameter("nome", nome);
            Cardapio consultado = query.getSingleResult();
            if (consultado != null) {
                em.remove(consultado);
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            return false;
        }
    }
}