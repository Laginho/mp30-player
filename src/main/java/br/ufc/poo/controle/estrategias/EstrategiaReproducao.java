package br.ufc.poo.controle.estrategias;

import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;
import java.util.List;

public interface EstrategiaReproducao {
    Midia obterProxima(List<Midia> fila, Midia atual) throws MidiaNaoEncontradaException;

    Midia obterAnterior(List<Midia> fila, Midia atual) throws MidiaNaoEncontradaException;
}
