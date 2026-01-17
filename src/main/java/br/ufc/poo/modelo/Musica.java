package br.ufc.poo.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Musica extends Midia {

    private Player player;
    private Thread threadMusica;

    private String artista;
    private String album;
    // 1.Construtores

    // Construtor sem áudio (modo debug)
    // Ex.: new Musica("Bohemian Rhapsody", 354, "Queen", "A Night at the Opera");
    public Musica(String titulo, int duracao, String artista, String album) {
        super(titulo, null, duracao);
        this.artista = artista;
        this.album = album;
    }

    // Construtor simplificado (modo debug)
    public Musica(String titulo, int duracao, String caminho) {
        super(titulo, caminho, duracao);
        this.artista = "Desconhecido";
        this.album = "Desconhecido";
    }

    // Construtor (para arquivos MP3)
    // Ex.: new Musica("bohemian_rhapsody.mp3",
    // "/home/user/Musicas/bohemian_rhapsody.mp3");
    public Musica(String titulo, int duracao, String caminho, String artista, String album) {
        super(titulo, caminho, duracao);
        this.artista = (artista != null) ? artista : "Desconhecido";
        this.album = (album != null) ? album : "Desconhecido";
    }

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