package br.com.sistema.main;

import br.com.sistema.view.telaLogin;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            telaLogin tela = new telaLogin();

            tela.setVisible(true);
        });
    }
}