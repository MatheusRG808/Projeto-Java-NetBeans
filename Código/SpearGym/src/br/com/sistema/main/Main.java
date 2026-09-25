package br.com.sistema.main;

import br.com.sistema.view.FrmListaCliente;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            FrmListaCliente tela = new FrmListaCliente();

            tela.setVisible(true);
        });
    }
}