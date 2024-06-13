package com.artlanche.model.transaction;

import com.artlanche.model.dtos.PrecoCardapioDTO;
import com.artlanche.model.entities.PrecoCardapio;

import jakarta.persistence.EntityManager;

public class PrecoCardapioDAO {
    
    public static void cadastrarValorCardapio(PrecoCardapioDTO dto) {
        try(EntityManager em = Database.getPrecoCardapioManager()) {
            PrecoCardapio preco = new PrecoCardapio(dto);
            em.getTransaction().begin();
            em.persist(preco);
            em.getTransaction().commit();
        } catch(Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
