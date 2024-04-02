package com.artlanche.view;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Interface reutilizável para carregar outros
 * layouts FXML para a aplicação
 * 
 * Todos os layouts FXML devem estar inseridos no diretório resources/layouts
 * 
 * @since 1.0
 * @author Jean Maciel
 */
public interface Layout {
    

    /**
     * Carrega o arquivo FXML "arquivo.fxml" passado como argumento
     * @param layout - nome do arquivo FXML que será carregado
     * @return um objeto URL com o caminho relativo do layout.
     * @throws IllegalArgumentException caso o layout não consiga ser encontrado no path padrão: resources/layouts, 
     * ou o o argumento passado seja nulo
     */
    public static URL loader(String layout) {
        if (layout == null || layout.isBlank()) {
            throw new IllegalArgumentException("Arquivo de layout não encontrado");
        } else {
            String path = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "layouts" + File.separator + layout;
            try {
                return new File(path).toURI().toURL();
            } catch (MalformedURLException e) {
                throw new IllegalArgumentException("Arquivo de layout não encontrado");
            }
        }
    }

    /**
     * Carrega para o layout um icone para ser usado na janela, 
     * que está inserido no diretório padrão: resources/layouts
     * @param icon - o ícone que deve ser carregado
     * @return um objeto String com o caminho relativo do Ícone
     * @throws NullPointerException caso o icone passado como argumento seja nulo 
     */
    public static String iconLoader(String icon) {
        if (icon == null || icon.isBlank()) {
            throw new NullPointerException("Arquivo de icone não encontrado");
        } else {
            String path = System.getProperty("user.dir") + File.separator + "resources" + File.separator + "icons" + File.separator + icon;
            return new File(path).toURI().toString();
        }
    }
}
