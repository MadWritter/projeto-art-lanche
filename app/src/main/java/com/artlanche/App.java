package com.artlanche;

import com.artlanche.view.Layout;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import lombok.Getter;

/**
 * Classe principal que inicia a tela de login
 * e manuseia as demais telas JavaFX
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public class App extends Application {

    /**
     * Objeto que representa a tela principal da aplicação
     * Deve ser usado para gerar/carregar outros layouts.
     */
    @Getter
    private static Stage tela;

    /**
     * Carrega os módulos JavaFX da aplicação
     */
    @Override
    public void start(Stage stage) throws Exception {
        setTela(stage);
        carregarTelaInicial();
    }

    /**
     * Método main que será carregado pela classe Main
     */
    public static void main(String[] args) {
        launch(args); // recebe os mesmos argumentos da classe
    }

    private void carregarTelaInicial() throws Exception {
        // Layout da tela inicial de login
        Parent root = FXMLLoader.load(Layout.loader("AppLayout.fxml"));

        Image icone = new Image(Layout.iconLoader("hamburguer.png"));

        tela.getIcons().add(icone);
        tela.setTitle("Art Lanche");
        tela.setResizable(false);
        tela.setScene(new Scene(root));
        tela.show();
    }

    // Demais, somente setters

    public static void setTela(Stage valor) {
        if (valor == null) {
            throw new NullPointerException("A referência para tela é nula");
        } else {
            tela = valor;
        }
    }
}