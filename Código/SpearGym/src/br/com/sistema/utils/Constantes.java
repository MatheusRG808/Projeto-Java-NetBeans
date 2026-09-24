package br.com.sistema.utils;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Dias da semana e grupos musculares/exercícios ficam fixos aqui no
 * código (não em tabela no banco), como definido para este projeto.
 */
public class Constantes {

    private Constantes() {
    }

    /** Chave usada no JSON/banco -> rótulo exibido na tela. */
    public static final Map<String, String> DIAS_SEMANA = new LinkedHashMap<>();

    static {
        DIAS_SEMANA.put("segunda", "Segunda-feira");
        DIAS_SEMANA.put("terca", "Terça-feira");
        DIAS_SEMANA.put("quarta", "Quarta-feira");
        DIAS_SEMANA.put("quinta", "Quinta-feira");
        DIAS_SEMANA.put("sexta", "Sexta-feira");
        DIAS_SEMANA.put("sabado", "Sábado");
        DIAS_SEMANA.put("domingo", "Domingo");
    }

    /** Grupo muscular -> exercícios pré-cadastrados daquele grupo (RF07-RF08). */
    public static final Map<String, List<String>> GRUPOS_EXERCICIOS = new LinkedHashMap<>();

    static {
        GRUPOS_EXERCICIOS.put("Peito", Arrays.asList(
                "Supino reto", "Supino inclinado", "Crucifixo", "Crossover"));
        GRUPOS_EXERCICIOS.put("Costas", Arrays.asList(
                "Puxada frente", "Remada baixa", "Puxada alta", "Levantamento terra"));
        GRUPOS_EXERCICIOS.put("Ombro", Arrays.asList(
                "Desenvolvimento militar", "Elevação lateral", "Elevação frontal", "Remada alta"));
        GRUPOS_EXERCICIOS.put("Bíceps", Arrays.asList(
                "Rosca direta", "Rosca alternada", "Rosca Scott"));
        GRUPOS_EXERCICIOS.put("Tríceps", Arrays.asList(
                "Tríceps corda", "Tríceps testa", "Mergulho no banco"));
        GRUPOS_EXERCICIOS.put("Perna", Arrays.asList(
                "Agachamento", "Leg press", "Cadeira extensora", "Stiff"));
    }
}