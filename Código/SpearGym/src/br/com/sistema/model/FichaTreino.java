package br.com.sistema.model;

import br.com.sistema.utils.Constantes;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Ficha de treino: um único registro guarda a semana inteira
 * (chave = dia da semana, valor = DiaTreino daquele dia).
 */
public class FichaTreino {

    private Integer id;
    private Map<String, DiaTreino> treino = new LinkedHashMap<>();

    public FichaTreino() {
        for (String diaChave : Constantes.DIAS_SEMANA.keySet()) {
            treino.put(diaChave, new DiaTreino());
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Map<String, DiaTreino> getTreino() {
        return treino;
    }

    public void setTreino(Map<String, DiaTreino> treino) {
        this.treino = treino;
    }
}