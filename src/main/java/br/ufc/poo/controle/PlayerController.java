package br.ufc.poo.controle;

import br.ufc.poo.controle.estrategias.EstrategiaReproducao;
import br.ufc.poo.controle.estrategias.ReproducaoAleatoria;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.excecoes.MidiaJaTocandoException;
import br.ufc.poo.excecoes.MidiaNaoEncontradaException;
import br.ufc.poo.modelo.Midia;
import br.ufc.poo.modelo.Musica;
import br.ufc.poo.visao.TelaBiblioteca;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador principal do player MP30.
 * Gerencia a playlist, fila de reprodução e controles de mídia.
 * <p>
 * Utiliza o padrão Strategy para permitir diferentes modos de reprodução
 * (sequencial, aleatório, repetir) e o padrão Observer para notificações.
 * </p>
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see EstrategiaReproducao
 * @see Midia
 */
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
        midiaAtual = midia;

        if (midiaAtual == null) {
            throw new MidiaNaoEncontradaException("Mídia não encontrada para reprodução.");
        }

        if (midiaAtual.isReproduzindo()) {
            throw new MidiaJaTocandoException("A mídia '" + midia.getTitulo() + "' já está em reprodução.");
        }

        // Registra o listener para tocar a próxima quando acabar
        midiaAtual.setOnMidiaFinalizadaListener(() -> {
            try {
                System.out.println("[INFO] Música finalizada, tocando próxima...");
                proxima();
                if (tela != null) {
                    tela.tocarMidia(midiaAtual);
                }
            } catch (MidiaNaoEncontradaException e) {
                System.out.println("[INFO] Fim da playlist.");
            }
        });

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

    // Com a implementação do filtro, um método auxiliar tem que ser criado
    // no PlayerController para comunicar a ele o que fazer se um mídia passa no
    // filtro
    private boolean passaNoFiltro(Midia m) {
        if (tela == null || m == null)
            return true;

        if (tela.isSoMusicas() && m.isAudio())
            return false;

        if (tela.isSoAudios() && m.isMusica())
            return false;

        return true;
    }

    public void proxima() throws MidiaNaoEncontradaException {
        Midia proximaMidia = null;

        if (midiaAtual != null) {
            midiaAtual.parar();
        }

        // Fila de prioridade
        while (!filaReproducao.isEmpty()) {
            Midia candidata = filaReproducao.remove(0);
            if (passaNoFiltro(candidata)) {
                proximaMidia = candidata;
                if (tela != null) {
                    tela.limparFilaReproducao();
                }
                break;
            }
        }

        System.out.println("[DEBUG] Tentando pegar próxima música...");
        System.out.println("[DEBUG] Estratégia: " + estrategia.getClass().getSimpleName());

        // Playlist com estratégia
        if (proximaMidia == null && !playlistPrincipal.isEmpty()) {
            Midia candidata = midiaAtual;
            int tentativas = 0;

            do {
                try {
                    candidata = estrategia.obterProxima(playlistPrincipal, candidata);
                } catch (MidiaNaoEncontradaException e) {
                    candidata = null;
                    e.printStackTrace();
                }

                tentativas++;
            } while (candidata != null
                    && !passaNoFiltro(candidata)
                    && tentativas <= playlistPrincipal.size());

            if (candidata != null && passaNoFiltro(candidata)) {
                proximaMidia = candidata;
            }
        }

        if (proximaMidia == null) {
            for (Midia m : playlistPrincipal) {
                if (passaNoFiltro(m)) {
                    proximaMidia = m;
                    break;
                }
            }
        }

        // Tocar ou parar
        if (proximaMidia != null) {
            tocar(proximaMidia);
        } else if (midiaAtual != null) {
            midiaAtual.parar();
            midiaAtual = null;
        }
    }

    // Logicamente, o método anterior também precisa ser adaptado para o filtro
    public void anterior() throws MidiaNaoEncontradaException {
        Midia midiaAnterior = null;

        if (playlistPrincipal.isEmpty())
            throw new MidiaNaoEncontradaException("Playlist vazia.");

        if (midiaAtual != null) {
            midiaAtual.parar();
        }

        System.out.println("[DEBUG] Tentando pegar mídia anterior...");
        System.out.println("[DEBUG] Estratégia: " + estrategia.getClass().getSimpleName());

        // Busca pela estratégia respeitando filtro
        if (midiaAtual != null) {
            Midia candidata = midiaAtual;
            int tentativas = 0;

            do {
                try {
                    candidata = estrategia.obterAnterior(playlistPrincipal, candidata);
                } catch (MidiaNaoEncontradaException e) {
                    candidata = null;
                }

                tentativas++;
            } while (candidata != null
                    && !passaNoFiltro(candidata)
                    && tentativas <= playlistPrincipal.size());

            if (candidata != null && passaNoFiltro(candidata)) {
                midiaAnterior = candidata;
            }
        }

        // Fallback: primeira mídia válida no filtro (sentido inverso)
        if (midiaAnterior == null) {
            for (int i = playlistPrincipal.size() - 1; i >= 0; i--) {
                Midia m = playlistPrincipal.get(i);
                if (passaNoFiltro(m)) {
                    midiaAnterior = m;
                    break;
                }
            }
        }

        // Tocar ou parar
        if (midiaAnterior != null) {
            tocar(midiaAnterior);
        } else {
            midiaAtual = null;
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

    public void setMidiaAtual(Midia midia) {
        this.midiaAtual = midia;
    }

    public List<Midia> getFilaReproducao() {
        return filaReproducao;
    }

    public List<Midia> getPlaylistPrincipal() {
        return playlistPrincipal;
    }

    public void limpar() {
        filaReproducao.clear();
        midiaAtual = null;

        if (estrategia instanceof ReproducaoAleatoria) {
            estrategia = new ReproducaoAleatoria();
        }
    }
}