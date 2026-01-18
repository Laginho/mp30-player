package br.ufc.poo.modelo;

import br.ufc.poo.modelo.interfaces.OnMidiaFinalizadaListener;
import br.ufc.poo.modelo.interfaces.Reproduzivel;

/**
 * Classe abstrata que representa uma mídia reproduzível.
 * Serve como base para as classes {@link Musica} e {@link Audio}.
 * <p>
 * Implementa a interface {@link Reproduzivel} e fornece funcionalidades
 * comuns como controle de reprodução, duração e notificação de término.
 * </p>
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see Musica
 * @see Audio
 * @see Reproduzivel
 */
public abstract class Midia implements Reproduzivel {
    protected String titulo;
    protected String caminho;
    protected int duracao; // em segundos
    protected int tempoAtual; // em segundos
    protected boolean reproduzindo;
    protected OnMidiaFinalizadaListener listener;

    /**
     * Duração máxima em segundos para classificar uma mídia como música (10
     * minutos).
     */
    public static final int DURACAO_MAXIMA_MUSICA_SEGUNDOS = 600;

    /**
     * Construtor da classe Midia.
     * 
     * @param titulo  o título da mídia
     * @param caminho o caminho do arquivo de mídia no sistema
     * @param duracao a duração da mídia em segundos
     */
    public Midia(String titulo, String caminho, int duracao) {
        this.titulo = titulo;
        this.caminho = caminho;
        this.duracao = duracao;
        this.tempoAtual = 0;
        this.reproduzindo = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getTempoAtual() {
        return tempoAtual;
    }

    public void setTempoAtual(int tempoAtual) {
        this.tempoAtual = tempoAtual;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public boolean isReproduzindo() {
        return reproduzindo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    /**
     * Retorna a duração formatada em minutos e segundos.
     * 
     * @return String no formato "Xmin Ys"
     */
    public String getDuracaoUsual() {
        int minutos = duracao / 60;
        int segundos = duracao % 60;
        return minutos + "min " + segundos + "s";
    }

    /**
     * Inicia a reprodução da mídia.
     * Define o estado de reprodução como verdadeiro.
     */
    @Override
    public void reproduzir() {
        this.reproduzindo = true;
    }

    /**
     * Para a reprodução da mídia.
     * Define o estado de reprodução como falso.
     */
    @Override
    public void parar() {
        this.reproduzindo = false;
    }

    /**
     * Define o listener para notificação de término da mídia.
     * 
     * @param listener o listener a ser notificado quando a mídia terminar
     */
    public void setOnMidiaFinalizadaListener(OnMidiaFinalizadaListener listener) {
        this.listener = listener;
    }

    /**
     * Notifica o listener registrado que a mídia finalizou.
     * Utilizado internamente pelas subclasses.
     */
    protected void notificarFim() {
        if (listener != null) {
            listener.onMidiaFinalizada();
        }
    }

    /**
     * Retorna uma representação em String da mídia.
     * 
     * @return representação textual da mídia
     */
    public abstract String toString();

    /**
     * Verifica se a mídia é classificada como música.
     * A classificação é baseada na duração: mídias com até 10 minutos são músicas.
     * 
     * @return {@code true} se a duração for menor ou igual a 600 segundos
     */
    public boolean isMusica() {
        return this.duracao <= DURACAO_MAXIMA_MUSICA_SEGUNDOS;
    }

    /**
     * Verifica se a mídia é classificada como áudio (podcast, audiobook, etc.).
     * A classificação é baseada na duração: mídias com mais de 10 minutos são
     * áudios.
     * 
     * @return {@code true} se a duração for maior que 600 segundos
     */
    public boolean isAudio() {
        return this.duracao > DURACAO_MAXIMA_MUSICA_SEGUNDOS;
    }

}
