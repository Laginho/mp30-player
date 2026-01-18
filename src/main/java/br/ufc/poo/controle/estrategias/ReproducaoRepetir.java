package br.ufc.poo.controle.estrategias;

import br.ufc.poo.modelo.Midia;
import java.util.List;

/**
 * Estratégia de reprodução em repetição.
 * Repete continuamente a mídia atual, ignorando a navegação.
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see EstrategiaReproducao
 */
public class ReproducaoRepetir implements EstrategiaReproducao {

    @Override
    public Midia obterProxima(List<Midia> fila, Midia atual) {
        return atual;
    }

    @Override
    public Midia obterAnterior(List<Midia> fila, Midia atual) {
        return atual;
    }

}
