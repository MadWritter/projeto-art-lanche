package com.artlanche.model.database;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Classe responsável por realizar tarefas do banco de dados
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

    /**
     * Faz a checagem das tabelas do banco de dados. 
     * Todo SQL deve estar inserido em config/database/sql 
     * com declarações terminadas em ";", além de que os
     * arquivos que desejam ser executados para verificação precisam estar
     * inseridos neste método.
     */
    public static void check() {
        try (Connection con = getConnection()) {
            /*
             * verifiquem sempre se o sql inclui verificação se a tabela já não existe
             * CREATE TABLE IF NOT EXISTS ... etc
             */
            executeSQLFile(con, loadSql("create-table-usuarios.sql"));
            

            // toda verificação usa o método acima
            // e também deve estar acima do encerramento da conexão
            // que é este método abaixo
            con.close();
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Um ou mais Arquivos SQL não foram executados" + e.getMessage());
        }
    }

    /**
     * Gera uma conexão com o banco de dados
     * @return
     * @throws SQLException caso o driver não consiga a conexão
     */
    private static Connection getConnection() throws SQLException {

        // reuso dos mesmos dados que a classe Settings fornece
        Map<String, String> dados = Settings.configureDatabase();

        String url = dados.get("jakarta.persistence.jdbc.url");
        String user = dados.get("jakarta.persistence.jdbc.user");
        String password = dados.get("jakarta.persistence.jdbc.password");

        return DriverManager.getConnection(url, user, password);
    }

    /**
     * Executa o statement a partir de um arquivo SQL.
     * @param connection - o driver de conexão com o banco
     * @param filePath - o arquivo SQL que precisa ser executado
     * @throws SQLException - caso ocorra algum problema na transação
     * @throws IOException - caso não carregue o arquivo SQL
     */
    private static void executeSQLFile(Connection connection, String filePath) throws SQLException, IOException {
        try (Statement statement = connection.createStatement();
             BufferedReader reader = new BufferedReader(new FileReader(filePath))) {

            String line;
            StringBuilder query = new StringBuilder();

            while ((line = reader.readLine()) != null) { // enquanto a linha não for nula
                query.append(line); // monta uma linha com o que veio na linha de buffer do arquivo, até carregar por completo como string
                if (line.endsWith(";")) { // se terminar com ";"
                    statement.execute(query.toString()); // executa o statement com a string montada
                    query.setLength(0); // depois zera a string para um novo arquivo que poderá ser carregado
                }
            }
        }
    }

    /**
     * Carrega o arquivo sql. 
     * Vale ressaltar que todos os arquivos sql devem estar em
     * config/database/sql
     * @param sqlFile
     * @return
     */
    private static String loadSql(String sqlFile) {
        if (sqlFile == null || sqlFile.isBlank()) {
            throw new IllegalArgumentException("Arquivo de SQL não encontrado em config/database/sql");
        } else {
        // caminho relativo do arquivo sql solicitado
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator +
                        "database" + File.separator + "sql" + File.separator + sqlFile;
            return path;
        }
    }
}
