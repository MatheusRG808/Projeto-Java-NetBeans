package br.com.sistema.utils;

public class ValidacaoUtil {

    private ValidacaoUtil() {
    }

    public static boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    /** Mantém apenas dígitos, útil para normalizar o CPF antes de salvar. */
    public static String somenteNumeros(String texto) {
        if (texto == null) {
            return "";
        }
        return texto.replaceAll("[^0-9]", "");
    }

    public static boolean cpfValido(String cpf) {
        String numeros = somenteNumeros(cpf);
        return numeros.length() == 11;
    }
}