package br.ufc.poo.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class Audio extends Midia {

    private Player player;
    private Thread threadAudio;

    private String autor;

    // Construtor simplificado (modo debug)
    public Audio(String titulo, int duracao, String caminho) {
        super(titulo, caminho, duracao);
        this.autor = "Desconhecido";
    }

    // Construtor (para arquivos MP3)
    // Ex.: new Musica("bohemian_rhapsody.mp3",
    // "/home/user/Musicas/bohemian_rhapsody.mp3");
    public Audio(String titulo, int duracao, String caminho, String autor) {
        super(titulo, caminho, duracao);
        this.autor = (autor != null) ? autor : "Desconhecido";
    }

    @Override
    public void reproduzir() {
        if (this.caminho == null) {
            System.out.println("Áudio em modo debug: " + titulo);
            return;
        }

        if (reproduzindo) {
            System.out.println("Áudio já está em reprodução: " + titulo);
            return;
        }

        super.reproduzir();

        if (threadAudio != null && threadAudio.isAlive()) {
            System.out.println("Áudio já está sendo reproduzido em outra thread: " + titulo);
            return;
        }

        threadAudio = new Thread(() -> {
            try {
                FileInputStream fis = new FileInputStream(caminho);
                player = new Player(fis);
                player.play();
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

        threadAudio.start();
        System.out.println("Reproduzindo áudio: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Duração: " + getDuracaoUsual());
    }

    @Override
    public void parar() {
        super.parar();

        if (player != null) {
            player.close();
        }

        System.out.println("Áudio parado: " + titulo);
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String toString() {
        return titulo + " - " + autor;
    }
}