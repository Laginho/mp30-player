package br.ufc.poo.controle.estrategias;

import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Estratégia de reprodução aleatória (shuffle).
 * Embaralha a playlist e reproduz as mídias em ordem aleatória.
 * Mantém um histórico para permitir navegação anterior/próxima.
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see EstrategiaReproducao
 */
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

        // Se a lista precisa ser embaralhada
        if (listaEmbaralhada.isEmpty() || listaEmbaralhada.size() != playlistOriginal.size()) {
            embaralhar(playlistOriginal, atual);
            // indiceAtual = 0 (música atual), então retornamos índice 1 (próxima)
            if (listaEmbaralhada.size() > 1) {
                indiceAtual = 1;
                return listaEmbaralhada.get(1);
            }
            return listaEmbaralhada.get(0);
        }

        indiceAtual++;
        indiceAtual %= listaEmbaralhada.size();

        return listaEmbaralhada.get(indiceAtual);
    }

    @Override
    public Midia obterAnterior(List<Midia> playlistOriginal, Midia midiaAtual) throws MidiaNaoEncontradaException {
        if (playlistOriginal == null || playlistOriginal.isEmpty()) {
            throw new MidiaNaoEncontradaException("Playlist vazia ou nula.");
        }

        if (listaEmbaralhada.isEmpty() || listaEmbaralhada.size() != playlistOriginal.size()) {
            embaralhar(playlistOriginal, midiaAtual);
        }

        indiceAtual--;

        if (indiceAtual < 0) {
            indiceAtual = listaEmbaralhada.size() - 1;
        }

        System.out.println("[DEBUG] Voltando para a música anterior: " + listaEmbaralhada.get(indiceAtual).getTitulo());
        return listaEmbaralhada.get(indiceAtual);
    }

    // A mídia atual é colocada na posição 0, o resto é aleatório
    private void embaralhar(List<Midia> original, Midia midiaAtual) {
        // Se não há mídia atual, usa a primeira da playlist
        if (midiaAtual == null) {
            midiaAtual = original.get(0);
        }

        final Midia primeiraMusica = midiaAtual;
        boolean check;

        do {
            // Cria lista com todas as músicas EXCETO a atual
            ArrayList<Midia> listaParcial = new ArrayList<>();
            for (Midia m : original) {
                if (!m.equals(primeiraMusica)) {
                    listaParcial.add(m);
                }
            }
            Collections.shuffle(listaParcial);

            listaEmbaralhada.clear();
            listaEmbaralhada.add(primeiraMusica); // A música atual é a primeira (índice 0)
            listaEmbaralhada.addAll(listaParcial);
            indiceAtual = 0;

            check = listaEmbaralhada.equals(original) ||
                    listaEmbaralhada.equals(original.reversed());

        } while (check && original.size() > 2); // Evita loop infinito com playlists pequenas

        System.out.println("[DEBUG] Playlist re-embaralhada para modo aleatório.");
        System.out.println("[DEBUG] Nova ordem:");
        for (int i = 0; i < listaEmbaralhada.size(); i++) {
            System.out.println("   [" + i + "] " + listaEmbaralhada.get(i).getTitulo());
        }
    }
}