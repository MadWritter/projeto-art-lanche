package com.artlanche;

import com.artlanche.model.database.Database;

/**
 * Classe inicial do programa
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class Main {
    
    public static void main(String[] args) {
        Database.check(); // faz a verificação do banco antes de iniciar o sistema.
        App.main(args);
    }
}
