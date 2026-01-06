package br.ufc.poo.controle;

import br.ufc.poo.controle.estrategias.EstrategiaReproducao;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.modelo.Midia;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    private List<Midia> playlistPrincipal;
    private List<Midia> filaReproducao;
    private Midia midiaAtual;
    private EstrategiaReproducao estrategia;

    public PlayerController() {
        this.playlistPrincipal = new ArrayList<>();
        this.filaReproducao = new ArrayList<>();
        this.estrategia = new ReproducaoSequencial();
    }

    // --- Gerenciamento das Listas ---

    public void adicionarNaPlaylist(Midia midia) {
        playlistPrincipal.add(midia);
    }

    public void adicionarNaFila(Midia midia) {
        filaReproducao.add(midia);
    }

    // --- Controle de Reprodução ---

    public void tocar(Midia midia) {
        if (midiaAtual != null) {
            midiaAtual.pausar();
        }

        midiaAtual = midia;
        midiaAtual.reproduzir();
    }

    public void proxima() {
        Midia proximaMidia = null;

        // A fila de prioridade tem preferência
        if (!filaReproducao.isEmpty()) {
            proximaMidia = filaReproducao.remove(0);
            System.out.println("[INFO] Tocando da Fila de Prioridade...");
        } else {
            proximaMidia = estrategia.obterProxima(playlistPrincipal, midiaAtual);
        }

        if (proximaMidia != null) {
            tocar(proximaMidia);
        } else {
            System.out.println("Fim da playlist.");
            if (midiaAtual != null) {
                midiaAtual.pausar();
            }
        }
    }

    public void pausar() {
        if (midiaAtual != null) {
            midiaAtual.pausar();
        }
    }

    // --- Configuração ---

    public void setEstrategia(EstrategiaReproducao novaEstrategia) {
        this.estrategia = novaEstrategia;
        System.out.println("Modo de reprodução alterado para: " + novaEstrategia.getClass().getSimpleName());
    }

    public Midia getMidiaAtual() {
        return midiaAtual;
    }
}