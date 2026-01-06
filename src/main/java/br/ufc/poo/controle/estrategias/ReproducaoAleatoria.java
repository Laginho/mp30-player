package br.ufc.poo.controle.estrategias;

import br.ufc.poo.modelo.Midia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* Aqui, usamos o algoritmo de Smart Shuffle

Em vez de escolher aleatoriamente a cada vez, de forma naive, é definida
uma fila aleatória, mas sua ordem é mantida constante durante a reprodução.
*/
public class ReproducaoAleatoria implements EstrategiaReproducao {

    private List<Midia> listaEmbaralhada;
    private int indiceAtual;

    public ReproducaoAleatoria() {
        this.listaEmbaralhada = new ArrayList<>();
        this.indiceAtual = -1;
    }

    @Override
    public Midia obterProxima(List<Midia> playlistOriginal, Midia atual) {
        if (playlistOriginal == null || playlistOriginal.isEmpty()) {
            return null;
        }

        if (listaEmbaralhada.isEmpty() || listaEmbaralhada.size() != playlistOriginal.size()) {
            embaralhar(playlistOriginal);
        }

        indiceAtual++;

        if (indiceAtual >= listaEmbaralhada.size()) {
            listaEmbaralhada.clear();
            return null;
        }

        return listaEmbaralhada.get(indiceAtual);
    }

    private void embaralhar(List<Midia> original) {
        listaEmbaralhada = new ArrayList<>(original);
        Collections.shuffle(listaEmbaralhada);
        indiceAtual = -1;
        System.out.println("[DEBUG] Playlist re-embaralhada para modo aleatório.");
    }
}
