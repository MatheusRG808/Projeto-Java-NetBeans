package br.com.sistema.utils;

import br.com.sistema.model.DiaTreino;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

public class TreinoJsonUtil {

    private static final Gson GSON = new Gson();

    private TreinoJsonUtil() {
    }

    /**
     * Converte o treino da ficha para JSON.
     */
    public static String paraJson(Map<String, DiaTreino> treino) {

        if (treino == null) {
            return "{}";
        }

        return GSON.toJson(treino);
    }

    /**
     * Converte o JSON salvo no banco novamente para o
     * Map<String, DiaTreino> utilizado pela FichaTreino.
     */
    public static Map<String, DiaTreino> deJson(String json) {

        if (json == null || json.trim().isEmpty()) {
            return new LinkedHashMap<>();
        }

        Type tipo = new TypeToken<Map<String, DiaTreino>>() {
        }.getType();

        Map<String, DiaTreino> treino = GSON.fromJson(json, tipo);

        if (treino == null) {
            return new LinkedHashMap<>();
        }

        return treino;
    }
}