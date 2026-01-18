package br.ufc.poo.controle.estrategias;

import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;
import java.util.List;

/**
 * Interface que define o padrão Strategy para modos de reprodução.
 * Permite trocar o algoritmo de seleção de mídias em tempo de execução.
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see ReproducaoSequencial
 * @see ReproducaoAleatoria
 * @see ReproducaoRepetir
 */
public interface EstrategiaReproducao {

    /**
     * Obtém a próxima mídia a ser reproduzida conforme a estratégia.
     * 
     * @param fila  a lista de mídias disponíveis
     * @param atual a mídia atualmente em reprodução
     * @return a próxima mídia a ser reproduzida
     * @throws MidiaNaoEncontradaException se não houver próxima mídia disponível
     */
    Midia obterProxima(List<Midia> fila, Midia atual) throws MidiaNaoEncontradaException;

    /**
     * Obtém a mídia anterior conforme a estratégia.
     * 
     * @param fila  a lista de mídias disponíveis
     * @param atual a mídia atualmente em reprodução
     * @return a mídia anterior
     * @throws MidiaNaoEncontradaException se não houver mídia anterior disponível
     */
    Midia obterAnterior(List<Midia> fila, Midia atual) throws MidiaNaoEncontradaException;
}
