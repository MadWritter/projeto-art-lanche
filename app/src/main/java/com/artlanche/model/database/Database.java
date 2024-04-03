package com.artlanche.model.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe responsável por trazer uma instância
 * de EntityManager baseado em uma entidade.
 * 
 * Todas as unidades de persistência devem ser configuradas pela 
 * interface Settings
 * @since 1.0
 * @author Jean Maciel
 */
public class Database {
    
    /**
     * Carrega um Entity Manager baseado na entidade Usuário
     * @return uma instância de um Entity Manager
     */
    public static EntityManager getUserManager() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("usuarios", Settings.configureDatabase());
        return emf.createEntityManager();
    }

    // TODO fazer a verificação do banco de dados
    // a verificação deve ser feita somente ao iniciar o sistema
    // a criação das tabelas deve verificar se a tabela não existe
    // precisa de uma conexão jdbc
    public static void check() {

    }
}
