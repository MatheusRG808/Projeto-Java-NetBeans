package br.com.sistema.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Pequenos utilitários de estilo para deixar as telas mais uniformes e
 * agradáveis, mantendo tudo em Swing puro (sem bibliotecas externas).
 */
public class UiTheme {

    public static final Color COR_PRIMARIA = new Color(0x1E, 0x88, 0x4C);
    public static final Color COR_PRIMARIA_ESCURA = new Color(0x14, 0x5C, 0x34);
    public static final Color COR_FUNDO = new Color(0xF4, 0xF6, 0xF5);
    public static final Color COR_CARTAO = Color.WHITE;
    public static final Color COR_TEXTO = new Color(0x22, 0x28, 0x24);
    public static final Color COR_PERIGO = new Color(0xC6, 0x28, 0x28);

    public static final Font FONTE_TITULO = new Font("SansSerif", Font.BOLD, 22);
    public static final Font FONTE_SUBTITULO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONTE_LABEL = new Font("SansSerif", Font.BOLD, 12);
    public static final Font FONTE_CAMPO = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONTE_BOTAO = new Font("SansSerif", Font.BOLD, 13);

    private UiTheme() {
    }

    public static void aplicarTemaNimbus() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                }
            }
            UIManager.put("control", COR_FUNDO);
            UIManager.put("nimbusBase", COR_PRIMARIA_ESCURA);
            UIManager.put("nimbusBlueGrey", new Color(0xE3, 0xE8, 0xE5));
            UIManager.put("nimbusFocus", COR_PRIMARIA);
            UIManager.put("nimbusSelectionBackground", COR_PRIMARIA);
            UIManager.put("nimbusSelectedText", Color.WHITE);
            UIManager.put("text", COR_TEXTO);
            UIManager.put("Table.alternateRowColor", new Color(0xEE, 0xF3, 0xEF));
        } catch (Exception erro) {
            System.err.println("Não foi possível aplicar o tema: " + erro.getMessage());
        }
    }

    public static JLabel criarTitulo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(FONTE_TITULO);
        label.setForeground(COR_PRIMARIA_ESCURA);
        return label;
    }

    public static JPanel criarCartao(String titulo) {
        JPanel painel = new JPanel();
        painel.setBackground(COR_CARTAO);
        Border linha = BorderFactory.createLineBorder(new Color(0xDD, 0xE2, 0xDF), 1, true);
        Border titulado = BorderFactory.createTitledBorder(linha, titulo,
                TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION,
                FONTE_LABEL, COR_PRIMARIA_ESCURA);
        Border espaco = BorderFactory.createEmptyBorder(10, 12, 12, 12);
        painel.setBorder(BorderFactory.createCompoundBorder(titulado, espaco));
        return painel;
    }

    public static void estilizarBotaoPrimario(JButton botao) {
        estilizarBotao(botao, COR_PRIMARIA, Color.WHITE);
    }

    public static void estilizarBotaoSecundario(JButton botao) {
        estilizarBotao(botao, Color.WHITE, COR_PRIMARIA_ESCURA);
        botao.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_PRIMARIA, 1, true),
                BorderFactory.createEmptyBorder(7, 15, 7, 15)));
    }

    public static void estilizarBotaoPerigo(JButton botao) {
        estilizarBotao(botao, COR_PERIGO, Color.WHITE);
    }

    private static void estilizarBotao(JButton botao, Color fundo, Color texto) {
        botao.setUI(new BasicButtonUI());
        botao.setFont(FONTE_BOTAO);
        botao.setBackground(fundo);
        botao.setForeground(texto);
        botao.setOpaque(true);
        botao.setContentAreaFilled(true);
        botao.setBorderPainted(false);
        botao.setFocusPainted(false);
        botao.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        botao.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
    }

    public static void aplicarFundo(Component componente) {
        if (componente instanceof JPanel) {
            ((JPanel) componente).setBackground(COR_FUNDO);
        }
    }
}