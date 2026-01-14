package br.ufc.poo.controle;

import br.ufc.poo.controle.estrategias.EstrategiaReproducao;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.excecoes.MidiaJaTocandoException;
import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
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

    public void tentar_tocar(Midia midia) throws MidiaJaTocandoException, MidiaNaoEncontradaException {
        if (midiaAtual != null) {
            midiaAtual.parar();
        }

        midiaAtual = midia;

        if (midiaAtual == null) {
            throw new MidiaNaoEncontradaException("Mídia não encontrada para reprodução.");
        }

        if (midiaAtual.isReproduzindo()) {
            throw new MidiaJaTocandoException("A mídia '" + midia.getTitulo() + "' já está em reprodução.");
        }

        midiaAtual.reproduzir();

        System.out.println("[INFO] Tocando agora: " + midiaAtual.getTitulo());
    }

    public void tocar(Midia midia) {
        try {
            tentar_tocar(midia);
        } catch (MidiaJaTocandoException e) {
            System.out.println("[ERROR] " + e.getMessage());
        } catch (MidiaNaoEncontradaException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        if (midiaAtual != null) {
            midiaAtual.parar();
        }
    }

    public void proxima() {
        Midia proximaMidia = null;

        // A fila de prioridade tem preferência
        if (!filaReproducao.isEmpty()) {
            proximaMidia = filaReproducao.remove(0);
            System.out.println("[INFO] Tocando da Fila de Prioridade: " + proximaMidia.getTitulo());
        } else {
            proximaMidia = estrategia.obterProxima(playlistPrincipal, midiaAtual);
        }

        if (midiaAtual != null) {
            midiaAtual.parar();
        }

        tocar(proximaMidia);
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
            Midia anterior = playlistPrincipal.get(playlistPrincipal.size() - 1);

            tocar(anterior);
        }
    }

    public void parar() {
        if (midiaAtual != null) {
            midiaAtual.parar();
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

    public List<Midia> getFilaReproducao() {
        return filaReproducao;
    }
}