package br.com.sistema.utils;

import br.com.sistema.model.DiaTreino;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TreinoJsonUtil {

    private TreinoJsonUtil() {
    }

    public static String paraJson(Map<String, DiaTreino> treino) {
        Map<String, Object> raiz = new LinkedHashMap<>();
        for (Map.Entry<String, DiaTreino> entrada : treino.entrySet()) {
            DiaTreino dia = entrada.getValue();
            Map<String, Object> diaJson = new LinkedHashMap<>();
            diaJson.put("grupos", dia.getGrupos());
            diaJson.put("exercicios", dia.getExercicios());
            diaJson.put("descricao", dia.getDescricao() == null ? "" : dia.getDescricao());
            raiz.put(entrada.getKey(), diaJson);
        }
        return JsonUtil.escrever(raiz);
    }

    @SuppressWarnings("unchecked")
    public static Map<String, DiaTreino> deJson(String json) {
        Map<String, DiaTreino> treino = new LinkedHashMap<>();
        if (json == null) {
            return treino;
        }
        Object raizObj = JsonUtil.ler(json);
        if (!(raizObj instanceof Map)) {
            return treino;
        }
        Map<String, Object> raiz = (Map<String, Object>) raizObj;
        for (Map.Entry<String, Object> entrada : raiz.entrySet()) {
            Object valor = entrada.getValue();
            DiaTreino dia = new DiaTreino();
            if (valor instanceof Map) {
                Map<String, Object> diaJson = (Map<String, Object>) valor;
                Object grupos = diaJson.get("grupos");
                Object exercicios = diaJson.get("exercicios");
                Object descricao = diaJson.get("descricao");
                if (grupos instanceof List) {
                    for (Object g : (List<Object>) grupos) {
                        dia.getGrupos().add(String.valueOf(g));
                    }
                }
                if (exercicios instanceof List) {
                    for (Object e : (List<Object>) exercicios) {
                        dia.getExercicios().add(String.valueOf(e));
                    }
                }
                if (descricao != null) {
                    dia.setDescricao(String.valueOf(descricao));
                }
            }
            treino.put(entrada.getKey(), dia);
        }
        return treino;
    }
}