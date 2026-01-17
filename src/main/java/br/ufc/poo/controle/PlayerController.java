package br.ufc.poo.controle;

import br.ufc.poo.controle.estrategias.EstrategiaReproducao;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.excecoes.MidiaJaTocandoException;
import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;
import br.ufc.poo.modelo.Musica;
import br.ufc.poo.visao.TelaBiblioteca;

import java.util.ArrayList;
import java.util.List;

public class PlayerController {

    private List<Midia> playlistPrincipal;
    private List<Midia> filaReproducao;
    private Midia midiaAtual;
    private EstrategiaReproducao estrategia;
    private TelaBiblioteca tela; // atributo adicionado para resolver a questão da limpeza da fila

    public PlayerController() {
        this.playlistPrincipal = new ArrayList<>();
        this.filaReproducao = new ArrayList<>();
        this.estrategia = new ReproducaoSequencial();
    }

    public void setTela(TelaBiblioteca tela) {
        this.tela = tela;
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
            System.out.println("[INFO] Parando mídia atual: " + midiaAtual.getTitulo());
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

    }

    public void proxima() {

        // A fila de prioridade tem preferência
        if (!filaReproducao.isEmpty()) {
            midiaAtual = filaReproducao.get(0);
            filaReproducao.remove(0);
            System.out.println("[INFO] Tocando da Fila de Prioridade: " + midiaAtual.getTitulo());
            if (tela != null) {
                tela.limparFilaReproducao(); // Ajusta para sincronizar a fila
            }
        } else {
            try {
                midiaAtual = estrategia.obterProxima(playlistPrincipal, midiaAtual);
            } catch (MidiaNaoEncontradaException e) {
                System.out.println("[ERROR] " + e.getMessage());
            }
        }
        if (midiaAtual == null && !playlistPrincipal.isEmpty()) {
            System.out.println("[INFO] Fim da playlist. Reiniciando...");
            midiaAtual = playlistPrincipal.get(0);
        }
        if (midiaAtual != null) {
            tocar(midiaAtual);
        } else {
            // caso extremo: playlist vazia
            if (midiaAtual != null) {
                midiaAtual.parar();
                midiaAtual = null;
            }
        }
    }

    public void anterior() {
        try {
            midiaAtual = estrategia.obterAnterior(playlistPrincipal, midiaAtual);
        } catch (MidiaNaoEncontradaException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }

        if (midiaAtual != null) {
            tocar(midiaAtual);
        } else {
            // caso extremo: playlist vazia
            if (midiaAtual != null) {
                midiaAtual.parar();
                midiaAtual = null;
            }
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