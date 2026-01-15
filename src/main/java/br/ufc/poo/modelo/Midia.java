package br.ufc.poo.modelo;

import br.ufc.poo.modelo.interfaces.Reproduzivel;

public abstract class Midia implements Reproduzivel {
    protected String titulo;
    protected String caminho;
    protected int duracao; // em segundos
    protected int tempoAtual; // em segundos
    protected boolean reproduzindo;

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

    public String getDuracaoUsual() {
        int minutos = duracao / 60;
        int segundos = duracao % 60;
        return minutos + "min " + segundos + "s";
    }

    @Override
    public void reproduzir() {
        this.reproduzindo = true;
    }

    @Override
    public void parar() {
        this.reproduzindo = false;
    }

    public abstract String toString();
    
    // Como proposto na reunião, a distinção de aúdio e música
    // vai ser feita com base no tempo de duração 
    public boolean isMusica() {
        return this.duracao <= 600; // 10 minutos como base 
    }
    public boolean isAudio() {
        return this.duracao > 600; 
    }

}
