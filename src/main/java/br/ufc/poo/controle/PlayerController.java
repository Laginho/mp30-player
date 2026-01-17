package br.ufc.poo.controle;

import br.ufc.poo.controle.estrategias.EstrategiaReproducao;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.excecoes.LimiteDaPlaylistException;
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

    public void proxima() {
        Midia proximaMidia = null;

        // 1Fila de prioridade
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
                } catch (LimiteDaPlaylistException e) {
                    candidata = playlistPrincipal.get(0);
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
    public void anterior() {
        if (playlistPrincipal.isEmpty())
            return;

        int indexAtual;

        // Se não há mídia atual, começa do fim
        if (midiaAtual == null) {
            indexAtual = playlistPrincipal.size();
        } else {
            indexAtual = playlistPrincipal.indexOf(midiaAtual);
        }

        // Volta procurando alguém que passe no filtro
        for (int i = indexAtual - 1; i >= 0; i--) {
            Midia candidata = playlistPrincipal.get(i);

            if (passaNoFiltro(candidata)) {
                tocar(candidata);
                return;
            }
        }

        // Se chegou no início e não achou, faz loop do final
        for (int i = playlistPrincipal.size() - 1; i >= 0; i--) {
            Midia candidata = playlistPrincipal.get(i);

            if (passaNoFiltro(candidata)) {
                tocar(candidata);
                return;
            }
        }

        // Se nada passou no filtro,para
        if (midiaAtual != null) {
            midiaAtual.parar();
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

    public List<Midia> getFilaReproducao() {
        return filaReproducao;
    }
}