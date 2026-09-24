package br.com.sistema.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Utilitário simples para converter entre estruturas Java (Map, List,
 * String) e texto JSON, sem depender de bibliotecas externas.
 * Suporta o suficiente do formato JSON para as necessidades deste
 * sistema: objetos, arrays, strings, números, booleanos e null.
 */
public class JsonUtil {

    private JsonUtil() {
    }

    // ---------- ESCRITA ----------

    public static String escrever(Object valor) {
        StringBuilder sb = new StringBuilder();
        escreverValor(valor, sb);
        return sb.toString();
    }

    @SuppressWarnings("unchecked")
    private static void escreverValor(Object valor, StringBuilder sb) {
        if (valor == null) {
            sb.append("null");
        } else if (valor instanceof Map) {
            escreverObjeto((Map<String, Object>) valor, sb);
        } else if (valor instanceof List) {
            escreverArray((List<Object>) valor, sb);
        } else if (valor instanceof Boolean || valor instanceof Number) {
            sb.append(valor.toString());
        } else {
            escreverString(valor.toString(), sb);
        }
    }

    private static void escreverObjeto(Map<String, Object> mapa, StringBuilder sb) {
        sb.append('{');
        boolean primeiro = true;
        for (Map.Entry<String, Object> entrada : mapa.entrySet()) {
            if (!primeiro) {
                sb.append(',');
            }
            primeiro = false;
            escreverString(entrada.getKey(), sb);
            sb.append(':');
            escreverValor(entrada.getValue(), sb);
        }
        sb.append('}');
    }

    private static void escreverArray(List<Object> lista, StringBuilder sb) {
        sb.append('[');
        boolean primeiro = true;
        for (Object item : lista) {
            if (!primeiro) {
                sb.append(',');
            }
            primeiro = false;
            escreverValor(item, sb);
        }
        sb.append(']');
    }

    private static void escreverString(String texto, StringBuilder sb) {
        sb.append('"');
        for (int i = 0; i < texto.length(); i++) {
            char c = texto.charAt(i);
            switch (c) {
                case '"':
                    sb.append("\\\"");
                    break;
                case '\\':
                    sb.append("\\\\");
                    break;
                case '\n':
                    sb.append("\\n");
                    break;
                case '\r':
                    sb.append("\\r");
                    break;
                case '\t':
                    sb.append("\\t");
                    break;
                default:
                    if (c < 0x20) {
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
    }

    // ---------- LEITURA ----------

    public static Object ler(String json) {
        Parser parser = new Parser(json == null ? "" : json);
        parser.pularEspacos();
        if (parser.acabou()) {
            return new LinkedHashMap<String, Object>();
        }
        return parser.lerValor();
    }

    private static class Parser {

        private final String texto;
        private int pos;

        Parser(String texto) {
            this.texto = texto;
        }

        boolean acabou() {
            return pos >= texto.length();
        }

        void pularEspacos() {
            while (!acabou() && Character.isWhitespace(texto.charAt(pos))) {
                pos++;
            }
        }

        char atual() {
            return texto.charAt(pos);
        }

        Object lerValor() {
            pularEspacos();
            char c = atual();
            if (c == '{') {
                return lerObjeto();
            } else if (c == '[') {
                return lerArray();
            } else if (c == '"') {
                return lerString();
            } else if (texto.startsWith("true", pos)) {
                pos += 4;
                return Boolean.TRUE;
            } else if (texto.startsWith("false", pos)) {
                pos += 5;
                return Boolean.FALSE;
            } else if (texto.startsWith("null", pos)) {
                pos += 4;
                return null;
            } else {
                return lerNumero();
            }
        }

        Map<String, Object> lerObjeto() {
            Map<String, Object> mapa = new LinkedHashMap<>();
            pos++; // {
            pularEspacos();
            if (!acabou() && atual() == '}') {
                pos++;
                return mapa;
            }
            while (true) {
                pularEspacos();
                String chave = lerString();
                pularEspacos();
                pos++; // :
                Object valor = lerValor();
                mapa.put(chave, valor);
                pularEspacos();
                if (!acabou() && atual() == ',') {
                    pos++;
                } else {
                    break;
                }
            }
            pularEspacos();
            if (!acabou() && atual() == '}') {
                pos++;
            }
            return mapa;
        }

        List<Object> lerArray() {
            List<Object> lista = new ArrayList<>();
            pos++; // [
            pularEspacos();
            if (!acabou() && atual() == ']') {
                pos++;
                return lista;
            }
            while (true) {
                Object valor = lerValor();
                lista.add(valor);
                pularEspacos();
                if (!acabou() && atual() == ',') {
                    pos++;
                } else {
                    break;
                }
            }
            pularEspacos();
            if (!acabou() && atual() == ']') {
                pos++;
            }
            return lista;
        }

        String lerString() {
            StringBuilder sb = new StringBuilder();
            pos++; // "
            while (!acabou() && atual() != '"') {
                char c = atual();
                if (c == '\\' && pos + 1 < texto.length()) {
                    pos++;
                    char esc = atual();
                    switch (esc) {
                        case '"':
                            sb.append('"');
                            break;
                        case '\\':
                            sb.append('\\');
                            break;
                        case '/':
                            sb.append('/');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            String hex = texto.substring(pos + 1, pos + 5);
                            sb.append((char) Integer.parseInt(hex, 16));
                            pos += 4;
                            break;
                        default:
                            sb.append(esc);
                    }
                } else {
                    sb.append(c);
                }
                pos++;
            }
            pos++; // "
            return sb.toString();
        }

        Object lerNumero() {
            int inicio = pos;
            while (!acabou() && "-+.eE0123456789".indexOf(atual()) >= 0) {
                pos++;
            }
            String numero = texto.substring(inicio, pos);
            if (numero.contains(".") || numero.contains("e") || numero.contains("E")) {
                return Double.parseDouble(numero);
            }
            return Long.parseLong(numero);
        }
    }
}