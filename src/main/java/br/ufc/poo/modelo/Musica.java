package br.ufc.poo.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Representa uma música no player MP30.
 * Estende {@link Midia} e adiciona atributos específicos como artista e álbum.
 * <p>
 * Utiliza a biblioteca JLayer para reprodução de arquivos MP3 em uma thread
 * separada,
 * permitindo reprodução assíncrona e não bloqueante.
 * </p>
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see Midia
 * @see Audio
 */
public class Musica extends Midia {

    private Player player;
    private Thread threadMusica;

    private String artista;
    private String album;

    /**
     * Construtor para modo debug (sem arquivo de áudio).
     * 
     * @param titulo  o título da música
     * @param duracao a duração em segundos
     * @param artista o nome do artista
     * @param album   o nome do álbum
     */
    public Musica(String titulo, int duracao, String artista, String album) {
        super(titulo, null, duracao);
        this.artista = artista;
        this.album = album;
    }

    /**
     * Construtor simplificado para modo debug.
     * 
     * @param titulo  o título da música
     * @param duracao a duração em segundos
     * @param caminho o caminho do arquivo MP3
     */
    public Musica(String titulo, int duracao, String caminho) {
        super(titulo, caminho, duracao);
        this.artista = "Desconhecido";
        this.album = "Desconhecido";
    }

    /**
     * Construtor completo para arquivos MP3.
     * 
     * @param titulo  o título da música
     * @param duracao a duração em segundos
     * @param caminho o caminho do arquivo MP3
     * @param artista o nome do artista (usa "Desconhecido" se nulo)
     * @param album   o nome do álbum (usa "Desconhecido" se nulo)
     */
    public Musica(String titulo, int duracao, String caminho, String artista, String album) {
        super(titulo, caminho, duracao);
        this.artista = (artista != null) ? artista : "Desconhecido";
        this.album = (album != null) ? album : "Desconhecido";
    }

    /**
     * Reproduz a música em uma thread separada.
     * <p>
     * Verifica se já está em reprodução antes de iniciar.
     * Ao finalizar naturalmente, notifica o listener registrado.
     * </p>
     */
    @Override
    public void reproduzir() {
        if (this.caminho == null) {
            System.out.println("Música em modo debug: " + titulo);
            return;
        }

        if (reproduzindo) {
            System.out.println("Música já está em reprodução: " + titulo);
            return;
        }

        if (threadMusica != null && threadMusica.isAlive()) {
            System.out.println("Música já está sendo reproduzida em outra thread: " + titulo);
            return;
        }

        super.reproduzir();

        threadMusica = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(caminho);
                player = new Player(fis);
                player.play();
                // Música terminou naturalmente
                if (this.reproduzindo) {
                    this.reproduzindo = false;
                    notificarFim();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        });

        threadMusica.start();
        System.out.println("Reproduzindo música: " + titulo);
        System.out.println("Artista: " + artista + " | Álbum: " + album);
        System.out.println("Duração: " + getDuracaoUsual());
    }

    /**
     * Para a reprodução da música e libera os recursos do player.
     */
    @Override
    public void parar() {
        super.parar();

        if (player != null) {
            player.close();
        }

        System.out.println("Música parada: " + titulo);
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String toString() {
        return titulo + " - " + artista;
    }
}