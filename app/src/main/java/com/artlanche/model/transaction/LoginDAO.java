package com.artlanche.model.transaction;

import java.util.List;

import com.artlanche.model.entities.Usuario;
import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.EntityManager;

/**
 * Classe responsável por pedir a autenticação do usuário no banco
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class LoginDAO {

    /**
     * Faz a autenticação do usuário no banco.
     * @param login - campo de login do usuário.
     * @param senhaInformada - campo de senha do usuário.
     * @return - um objeto Usuario do sistema.
     * @throws IllegalArgumentException caso o login ou senha passados como parâmetro
     * sejam nulos ou vazios.
     */
    public static Usuario fazerLogin(String login, String senhaInformada) {
        if (login.isBlank() || senhaInformada.isBlank()) {
            throw new IllegalArgumentException("Login ou senha informados são nulos ou vazios");
        }
        
        EntityManager em = Database.getUserManager();
        em.getTransaction().begin();

        // consulta com os dados fornecidos
        var query = em.createQuery("SELECT u FROM Usuario u WHERE u.login = :login",Usuario.class);

        query.setParameter("login", login);

        List<Usuario> resultList = query.getResultList();
        Usuario usuario;
        
        if (!resultList.isEmpty()) {
            usuario = resultList.get(0);
            em.getTransaction().commit();
            em.close();
            return validarSenha(usuario, senhaInformada);
        } else {
            em.getTransaction().commit();
            em.close();
            return null;
        }
    }

    /**
     * Faz a comparação do hash das senhas.
     * @param usuario - que foi consultado para comparar
     * @param senhaInformada - a senha que deseja comparar
     * @return um Usuario caso o hash seja igual ou null caso o hash seja diferente
     */
    private static Usuario validarSenha(Usuario usuario, String senhaInformada) {
        boolean validou = BCrypt.verifyer().verify(senhaInformada.toCharArray(), usuario.getSenha().toCharArray()).verified;
        if (validou) {
            return usuario;
        } else {
            return null;
        }
    }
}