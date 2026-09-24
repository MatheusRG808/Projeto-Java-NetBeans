package br.com.sistema.model;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * Representa o treino de um único dia da semana: os grupos musculares
 * escolhidos (RF10), os exercícios selecionados dentre os pré-cadastrados
 * daqueles grupos (RF11-RF12) e uma descrição livre.
 */
public class DiaTreino {
 
    private List<String> grupos = new ArrayList<>();
    private List<String> exercicios = new ArrayList<>();
    private String descricao = "";
 
    public List<String> getGrupos() {
        return grupos;
    }
 
    public void setGrupos(List<String> grupos) {
        this.grupos = grupos;
    }
 
    public List<String> getExercicios() {
        return exercicios;
    }
 
    public void setExercicios(List<String> exercicios) {
        this.exercicios = exercicios;
    }
 
    public String getDescricao() {
        return descricao;
    }
 
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
 
    public boolean isVazio() {
        return grupos.isEmpty() && exercicios.isEmpty() && (descricao == null);
    }
}