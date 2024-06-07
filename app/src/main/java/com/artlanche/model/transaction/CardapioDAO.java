package com.artlanche.model.transaction;

import java.util.ArrayList;
import java.util.List;

import com.artlanche.model.dtos.CardapioDTO;
import com.artlanche.model.entities.Cardapio;

import jakarta.persistence.EntityManager;

public class CardapioDAO {

    public static List<CardapioDTO> getListaCardapio() {
        try(EntityManager em = Database.getCardapioManager()) {
            List<CardapioDTO> listaDTO = new ArrayList<>();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.ativo=true", Cardapio.class);
            List<Cardapio> consulta = query.getResultList();
            if (consulta != null) {
                for(Cardapio c : consulta) {
                    var dto = new CardapioDTO(c);
                    listaDTO.add(dto);
                }
                return listaDTO;
            } else {
                throw new Exception();
            }
        } catch(Exception e) {
            return null;
        }
    }
    
    public static boolean adicionarItem(CardapioDTO dados) {
        Cardapio item = null;
        if (dados != null) {
            item = new Cardapio(dados);
        } else {
            throw new NullPointerException("O nome passado como argumento para o item é nulo");
        }

        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.descricaoItem=:nome AND c.ativo=true", Cardapio.class);
            query.setParameter("nome", dados.getDescricaoItem());
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

    public static boolean alterarItem(String nomeAntigo, CardapioDTO dados) {
        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.descricaoItem=:nomeAtual AND c.ativo=true", Cardapio.class);
            query.setParameter("nomeAtual", nomeAntigo);
            Cardapio item = query.getSingleResult();

            if (item != null) {
                item.setDescricao(dados.getDescricaoItem());
                item.setValorPorUnidade(dados.getValorPorUnidade());
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
                consultado.desativarItem();
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

    public static Cardapio getItemCardapioByDescricao(String item) {
        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.descricaoItem=:item", Cardapio.class);
            query.setParameter("item", item);
            return query.getSingleResult();
        } catch(Exception e) {
            return null;
        }
    }

    public static CardapioDTO getCardapioById(Long id) {
        try(EntityManager em = Database.getCardapioManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT c FROM Cardapio c WHERE c.id=:id", Cardapio.class);
            query.setParameter("id", id);
            Cardapio c = query.getSingleResult();
            return new CardapioDTO(c);
        } catch(Exception e) {
            throw new RuntimeException();
        }
    }
}
