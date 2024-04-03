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

    /**
     * Centraliza a tela da aplicação
     * Deve ser usada sempre que precisar gerar um novo layout
     */
    public static void centralize() {
        // recebe as dimensões da tela
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        // Calcula as coordenadas X e Y centrais da janela para centralizá-la na tela
        double centerX = (primaryScreenBounds.getWidth() - App.tela.getWidth()) / 2;
        double centerY = (primaryScreenBounds.getHeight() - App.tela.getHeight()) / 2;

        // Define a posição da janela
        tela.setX(centerX);
        tela.setY(centerY);
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