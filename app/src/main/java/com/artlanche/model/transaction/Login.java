package com.artlanche.model.transaction;

import java.util.List;

import com.artlanche.model.database.Database;
import com.artlanche.model.entities.Usuario;

import jakarta.persistence.EntityManager;

/**
 * Classe responsável por pedir a autenticação do usuário no banco
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class Login {

    /**
     * Faz a autenticação do usuário no banco.
     * @param login - campo de login do usuário.
     * @param senha - campo de senha do usuário.
     * @return - um objeto Usuario do sistema.
     * @throws IllegalArgumentException caso o login ou senha passados como parâmetro
     * sejam nulos ou vazios.
     */
    public static Usuario fazerLogin(String login, String senha) {
        if (login.isBlank() || senha.isBlank()) {
            throw new IllegalArgumentException("Login ou senha informados são nulos ou vazios");
        }
        
        EntityManager em = Database.getUserManager();
        em.getTransaction().begin();

        // consulta com os dados fornecidos
        var query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.senha = :senha",Usuario.class);

        query.setParameter("login", login);
        query.setParameter("senha", senha);

        List<Usuario> resultList = query.getResultList();
        Usuario usuario = null;
        
        if (!resultList.isEmpty()) {
            usuario = resultList.get(0);
        }

        em.getTransaction().commit();
        em.close();

        return usuario;
    }
}