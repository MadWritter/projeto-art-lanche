package com.artlanche.model.transaction;

import java.util.List;
import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.entities.Pedido;
import jakarta.persistence.EntityManager;

public class PedidoDAO {
    
    public static boolean cadastrarNovoPedido(PedidoDTO pedidoDTO) {
        Pedido pedido = new Pedido(pedidoDTO);
        try(EntityManager em = Database.getPedidoManager()) {
            em.getTransaction().begin();
            em.persist(pedido);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static Pedido getPedidoById(Long id) {
        try(EntityManager em = Database.getPedidoManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.id=:id", Pedido.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch(Exception e ){
            throw new RuntimeException();
        }
    }

    public static Pedido getPedidoByIdIfNaoConcluido(Long id) {
        try(EntityManager em = Database.getPedidoManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.id=:id AND concluido=false", Pedido.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch(Exception e ){
            throw new RuntimeException();
        }
    }

    public static List<Pedido> getListaPedidosNaoConcluidos(Long caixaId) {
        try(EntityManager em = Database.getPedidoManager()) {
            em.getTransaction().begin();
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.concluido = false AND p.caixaId = :caixaId", Pedido.class);
            query.setParameter("caixaId", caixaId);
            return query.getResultList();
        } catch(Exception e) {
            throw new RuntimeException("Erro ao consultar pedidos");
        }
    }
}
