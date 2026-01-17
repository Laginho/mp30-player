package br.ufc.poo.controle.estrategias;

import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Aqui, usamos o algoritmo de Smart Shuffle.
// Em vez de escolher aleatoriamente a cada vez, de forma naive, é definida
// uma fila aleatória, mas sua ordem é mantida constante durante a reprodução.
public class ReproducaoAleatoria implements EstrategiaReproducao {

    private List<Midia> listaEmbaralhada;
    private int indiceAtual;

    public ReproducaoAleatoria() {
        this.listaEmbaralhada = new ArrayList<>();
        this.indiceAtual = -1;
    }

    @Override
    public Midia obterProxima(List<Midia> playlistOriginal, Midia atual) throws MidiaNaoEncontradaException {
        if (playlistOriginal == null || playlistOriginal.isEmpty()) {
            throw new MidiaNaoEncontradaException("Playlist vazia ou nula.");
        }

        if (listaEmbaralhada.isEmpty() || listaEmbaralhada.size() != playlistOriginal.size()) {
            embaralhar(playlistOriginal);
        }

        indiceAtual++;
        indiceAtual %= playlistOriginal.size();

        return listaEmbaralhada.get(indiceAtual);
    }

    @Override
    public Midia obterAnterior(List<Midia> playlistOriginal, Midia midiaAtual) throws MidiaNaoEncontradaException {
        if (playlistOriginal == null || playlistOriginal.isEmpty()) {
            throw new MidiaNaoEncontradaException("Playlist vazia ou nula.");
        }

        if (listaEmbaralhada.isEmpty() || listaEmbaralhada.size() != playlistOriginal.size()) {
            embaralhar(playlistOriginal);
        }

        indiceAtual--;

        if (indiceAtual < 0) {
            indiceAtual = listaEmbaralhada.size() - 1;
        }

        return listaEmbaralhada.get(indiceAtual);
    }

    // A mídia atual deve ser a primeira, e o resto é aleatório
    private void embaralhar(List<Midia> original) {

        do {
            List<Midia> temp = original.subList(1, original.size());
            ArrayList<Midia> listaParcial = new ArrayList<>(temp);
            Collections.shuffle(listaParcial);

            listaEmbaralhada.clear();
            listaEmbaralhada.add(original.get(0));
            listaEmbaralhada.addAll(listaParcial);
            indiceAtual = 0;
        } while (listaEmbaralhada.equals(original)); // Evita a mesma ordem

        System.out.println("[DEBUG] Playlist re-embaralhada para modo aleatório.");
    }
}
