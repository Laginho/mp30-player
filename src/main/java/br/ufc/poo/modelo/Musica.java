package br.ufc.poo.modelo;

import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class Musica extends Midia {

    private Player player;
    private Thread threadMusica;

    private String artista;
    private String album;
    // 1.Construtores

    // Construtor para debug
    // Ex.: new Musica("Bohemian Rhapsody", 354, "Queen", "A Night at the Opera");
    public Musica(String titulo, int duracao, String artista, String album) {
        super(titulo, null, duracao);
        this.artista = artista;
        this.album = album;
    }

    // Construtor (para arquivos MP3)
    // Ex.: new Musica("bohemian_rhapsody.mp3",
    // "/home/user/Musicas/bohemian_rhapsody.mp3");
    public Musica(String titulo, int duracao, String caminho) {
        super(titulo, caminho, duracao);
        this.caminho = caminho;
        this.artista = "Desconhecido";
        this.album = "Desconhecido";
    }

    @Override
    public void reproduzir() {
        if (this.caminho == null) {
            System.out.println("Música em modo debug: " + titulo);
            return;
        }

        if (reproduzindo) {
            return; // Já está reproduzindo
        }

        super.reproduzir();

        if (threadMusica != null && threadMusica.isAlive()) {
            return; // Já está sendo reproduzida em outra thread
        }

        threadMusica = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(caminho);
                player = new Player(fis);
                player.play();
                this.reproduzindo = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadMusica.start();
        System.out.println("Reproduzindo música: " + titulo);
        System.out.println("Artista: " + artista + " | Álbum: " + album);
        System.out.println("Duração: " + getDuracaoUsual());
    }

    @Override
    public void pausar() {
        super.pausar();
        if (player != null) {
            player.close();
            threadMusica = null;
            System.out.println("Música pausada: " + titulo);
        }
    }

    // 2.Getters e Setters

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
}