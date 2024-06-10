package com.artlanche.model.transaction;

import java.util.ArrayList;
import java.util.List;

import com.artlanche.model.dtos.PedidoDTO;
import com.artlanche.model.entities.Pedido;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;

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
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.id=:id", Pedido.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch(Exception e){
            return null;
        }
    }

    public static Pedido getPedidoByIdIfNaoConcluido(Long id) {
        try(EntityManager em = Database.getPedidoManager()) {
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.id=:id AND concluido=false", Pedido.class);
            query.setParameter("id", id);
            return query.getSingleResult();
        } catch(Exception e ){
            return null;
        }
    }

    public static List<PedidoDTO> getListaPedidosNaoConcluidos(Long caixaId) {
        try(EntityManager em = Database.getPedidoManager()) {
            List<PedidoDTO> lista = new ArrayList<>();
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.concluido = false AND p.caixaId = :caixaId", Pedido.class);
            query.setParameter("caixaId", caixaId);
            List<Pedido> consulta = query.getResultList();
            if (consulta != null && !consulta.isEmpty()) {
                for(int i = 0; i < consulta.size(); i++) {
                    PedidoDTO pedido = new PedidoDTO(consulta.get(i));
                    lista.add(pedido);
                }
                return lista;
            } else {
                return null;
            }
        } catch(Exception e) {
            return null;
        }
    }

    public static List<PedidoDTO> getListaPedidosConcluidos(Long caixaId) {
        try(EntityManager em = Database.getPedidoManager()) {
            List<PedidoDTO> lista = new ArrayList<>();
            var query = em.createQuery("SELECT p FROM Pedido p WHERE p.concluido = true AND p.caixaId = :caixaId", Pedido.class);
            query.setParameter("caixaId", caixaId);
            List<Pedido> consulta = query.getResultList();
            if (consulta != null && !consulta.isEmpty()) {
                for(int i = 0; i < consulta.size(); i++) {
                    PedidoDTO pedido = new PedidoDTO(consulta.get(i));
                    lista.add(pedido);
                }
                return lista;
            } else {
                return null;
            }
        } catch(Exception e) {
            return null;
        }
    }

    public static boolean atualizarPedido(PedidoDTO pedido) {
        try(EntityManager em = Database.getPedidoManager()) {
            em.getTransaction().begin();
            Pedido consulta = em.find(Pedido.class, pedido.getId());
            if (consulta != null) {
                consulta.associarIDItemCardapio(pedido.getItensDoCardapio());
                consulta.setTextoComanda(pedido.getComanda());
                consulta.setValorComanda(pedido.getValorComanda());
                consulta.setDesconto(pedido.getDesconto());
                consulta.setTotal(pedido.getTotal());
                em.flush();
                em.getTransaction().commit();
                return true;
            } else {
                return false;
            }
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static boolean removerPedido(PedidoDTO pedido) {
        try(EntityManager em = Database.getPedidoManager()) {
            em.getTransaction().begin();
            Pedido pedidoParaRemover = em.find(Pedido.class, pedido.getId());
            em.remove(pedidoParaRemover);
            em.getTransaction().commit();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    public static boolean finalizarPedido(PedidoDTO pedidoParaFinalizar) {
        try(EntityManager em = Database.getPedidoManager()) {
            if (pedidoParaFinalizar != null) {
                em.getTransaction().begin();
                Pedido consulta = em.find(Pedido.class, pedidoParaFinalizar.getId());
                if (consulta != null) {
                    consulta.concluirPedido();
                    em.flush();
                    em.getTransaction().commit();
                    return true;
                } else {
                    throw new PersistenceException("Não existe esse pedido no banco");
                }
            } else {
                throw new NullPointerException("PedidoDTO nulo");
            }
        } catch(PersistenceException e) {
            throw new PersistenceException(e.getMessage());
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getMessage());
        }
    }
}
