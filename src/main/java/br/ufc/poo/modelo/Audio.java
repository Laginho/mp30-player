package br.ufc.poo.modelo;

import java.io.FileInputStream;
import javazoom.jl.player.Player;

public class Audio extends Midia {

    private Player player;
    private Thread threadAudio;

    private String artista;
    // 1.Construtores

    // Construtor simplificado (modo debug)
    public Audio(String titulo, int duracao, String caminho) {
        super(titulo, caminho, duracao);
        this.artista = "Desconhecido";
    }

    // Construtor (para arquivos MP3)
    // Ex.: new Musica("bohemian_rhapsody.mp3",
    // "/home/user/Musicas/bohemian_rhapsody.mp3");
    public Audio(String titulo, int duracao, String caminho, String artista) {
        super(titulo, caminho, duracao);
        this.artista = (artista != null) ? artista : "Desconhecido";
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

        if (threadAudio != null && threadAudio.isAlive()) {
            return; // Já está sendo reproduzida em outra thread
        }

        threadAudio = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(caminho);
                player = new Player(fis);
                player.play();
                this.reproduzindo = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        threadAudio.start();
        System.out.println("Reproduzindo áudio: " + titulo);
        System.out.println("Artista: " + artista);
        System.out.println("Duração: " + getDuracaoUsual());
    }

    @Override
    public void pausar() {
        super.pausar();
        if (player != null) {
            player.close();
            reproduzindo = false;
            threadAudio = null;
            System.out.println("Áudio pausado: " + titulo);
        }
    }

    // 2.Getters e Setters

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String toString() {
        return titulo + " - " + artista;
    }
}