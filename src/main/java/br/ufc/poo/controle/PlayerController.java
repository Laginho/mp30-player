package br.ufc.poo.controle;

import br.ufc.poo.controle.estrategias.EstrategiaReproducao;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.modelo.Midia;
import br.ufc.poo.modelo.Musica;

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

    // Só reproduz automaticamente as músicas, não os áudios
    public void adicionarNaPlaylist(Midia midia) {
        if (!(midia instanceof Musica)) {
            return;
        }

        playlistPrincipal.add(midia);
    }

    // Essa é manual, então não precisa filtrar
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

    public void anterior() {
        if (playlistPrincipal.isEmpty() || midiaAtual == null) {
            return;
        }

        int indexAtual = playlistPrincipal.indexOf(midiaAtual);

        if (indexAtual > 0) {
            if (midiaAtual.getTempoAtual() > 3) {
                tocar(midiaAtual);
                return;
            } else {
                Midia anterior = playlistPrincipal.get(indexAtual - 1);
                tocar(anterior);
            }

        } else {
            System.out.println("Início da playlist.");
            midiaAtual.pausar();
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

    // Esse get vai ajudar na criação da tela de fila de reprodução
    public List<Midia> getFilaReproducao() {
        return filaReproducao;
    }
}