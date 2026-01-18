package br.ufc.poo.modelo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * Representa um áudio de longa duração no player MP30 (podcasts, audiobooks,
 * etc.).
 * Estende {@link Midia} e é utilizada para mídias com mais de 10 minutos de
 * duração.
 * <p>
 * Diferente de {@link Musica}, possui apenas o atributo autor em vez de artista
 * e álbum.
 * </p>
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see Midia
 * @see Musica
 */
public class Audio extends Midia {

    private Player player;
    private Thread threadAudio;

    private String autor;

    /**
     * Construtor simplificado para modo debug.
     * 
     * @param titulo  o título do áudio
     * @param duracao a duração em segundos
     * @param caminho o caminho do arquivo MP3
     */
    public Audio(String titulo, int duracao, String caminho) {
        super(titulo, caminho, duracao);
        this.autor = "Desconhecido";
    }

    /**
     * Construtor completo para arquivos MP3.
     * 
     * @param titulo  o título do áudio
     * @param duracao a duração em segundos
     * @param caminho o caminho do arquivo MP3
     * @param autor   o nome do autor (usa "Desconhecido" se nulo)
     */
    public Audio(String titulo, int duracao, String caminho, String autor) {
        super(titulo, caminho, duracao);
        this.autor = (autor != null) ? autor : "Desconhecido";
    }

    /**
     * Reproduz o áudio em uma thread separada.
     * <p>
     * Verifica se já está em reprodução antes de iniciar.
     * Ao finalizar naturalmente, notifica o listener registrado.
     * </p>
     */
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

    /**
     * Para a reprodução do áudio e libera os recursos do player.
     */
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