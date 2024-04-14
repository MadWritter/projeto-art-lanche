package com.artlanche.model.transaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Esta interface é responsável por configurar as propriedades do banco de dados.
 * É exclusivamente feita para ser usada pela classe Database
 * 
 * @since 1.0
 * @author Jean Maciel
 */
interface Settings {

    /**
     * Configura as propriedades do banco de dados a partir do arquivo de configuração.
     * @return Um mapa contendo as configurações do banco de dados.
     * @throws IllegalArgumentException Se o arquivo de configuração do banco de dados não for encontrado.
     */
    static Map<String, String> configureDatabase() {
        Map<String, String> configuration = new HashMap<>();
        Properties data = loadProperties();

        configuration.put("jakarta.persistence.jdbc.url", data.getProperty("url"));
        configuration.put("jakarta.persistence.jdbc.user", data.getProperty("usr"));
        configuration.put("jakarta.persistence.jdbc.password", data.getProperty("pwd"));

        return configuration;
    }

    /**
     * Carrega as propriedades do arquivo de configuração do banco.
     * As configurações devem estar inseridas em config/database/connection.properties
     * @return Um objeto Properties contendo as propriedades carregadas.
     * @throws IllegalArgumentException Se o arquivo de configuração do banco de dados não for encontrado ou ocorrer um erro ao carregá-lo.
     */
    private static Properties loadProperties() {
        Properties prop = new Properties();
        String path = System.getProperty("user.dir") + File.separator + "config" + File.separator + "database" + File.separator + "connection.properties";

        try (InputStream input = new FileInputStream(path)) {
            prop.load(input);
            return prop;
        } catch (IOException e) {
            throw new IllegalArgumentException("Erro ao carregar o arquivo de configuração do banco de dados: " + e.getMessage());
        }
    }
}
