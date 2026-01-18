package br.ufc.poo.controle.estrategias;

import br.ufc.poo.modelo.Midia;
import java.util.List;

/**
 * Estratégia de reprodução sequencial.
 * Reproduz as mídias na ordem em que aparecem na playlist,
 * avançando para a próxima ou voltando para a anterior.
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see EstrategiaReproducao
 */
public class ReproducaoSequencial implements EstrategiaReproducao {

    @Override
    public Midia obterProxima(List<Midia> playlist, Midia atual) {
        if (playlist == null || playlist.isEmpty()) {
            return null;
        }

        if (atual == null) {
            return playlist.get(0);
        }

        int indiceAtual = playlist.indexOf(atual);

        if (indiceAtual == -1) {
            return playlist.get(0);
        }

        if (indiceAtual + 1 < playlist.size()) {
            return playlist.get(indiceAtual + 1);
        }

        return null;
    }

    @Override
    public Midia obterAnterior(List<Midia> playlist, Midia atual) {
        if (playlist == null || playlist.isEmpty()) {
            return null;
        }

        if (atual == null) {
            return playlist.get(0);
        }

        int indiceAtual = playlist.indexOf(atual);

        if (indiceAtual == -1) {
            return playlist.get(0);
        }

        if (indiceAtual - 1 >= 0) {
            return playlist.get(indiceAtual - 1);
        }

        return null;
    }
}