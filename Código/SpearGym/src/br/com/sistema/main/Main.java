package br.com.sistema.main;
 
import br.com.sistema.utils.UiTheme;
import br.com.sistema.view.FrmLogin;
import javax.swing.SwingUtilities;
 
public class Main {
 
    public static void main(String[] args) {
        UiTheme.aplicarTemaNimbus();
        SwingUtilities.invokeLater(() -> {
            FrmLogin login = new FrmLogin();
            login.setVisible(true);
        });
    }
}