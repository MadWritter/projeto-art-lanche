package com.artlanche.model.transaction;

import com.artlanche.model.database.Database;
import com.artlanche.model.entities.Usuario;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Classe responsável por cadastrar um usuário no banco
 *
 * @since 1.0
 * @author Jean Maciel
 */
public class Cadastrar {
    /**
     * Cadastra o usuário no banco
     * @param nome - obritagório
     * @param cpf - obrigatório
     * @param email - opcional
     * @param login - obrigatório
     * @param senha - obrigatório
     * @return true caso consiga cadastrar
     * @throws PersistenceException caso não consiga cadastrar no banco
     */
    public static boolean novoUsuario(String nome, String cpf, String email, String login, String senha) {
        // cifrar a senha
        String senhaCifrada = BCrypt.withDefaults().hashToString(12, senha.toCharArray());

        // dados para cadastrar no banco
        var dadosUsuario = new Usuario(nome, cpf, email, login, senhaCifrada);
        try(EntityManager em = Database.getUserManager()) {
            em.getTransaction().begin();
            em.persist(dadosUsuario);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            Alert alerta = new Alert(AlertType.ERROR);
            alerta.setTitle("Erro");
            alerta.setHeaderText("Erro ao cadastrar o usuário no banco, tente novamente");
            alerta.showAndWait();
            throw new PersistenceException("Erro ao cadastrar na base de dados" + e.getMessage());
        }
    }
}
