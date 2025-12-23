package br.ufc.poo.modelo;

import br.ufc.poo.modelo.interfaces.Reproduzivel;

public abstract class Midia implements Reproduzivel {
    protected String titulo;
    protected int duracao; // em segundos
    protected boolean favorita;
    protected boolean reproduzindo;
    protected int tempoAtual; // em segundos

    // 1. Construtor
    public Midia(String titulo, int duracao) {
        this.titulo = titulo;
        this.duracao = duracao;
        this.favorita = false;
        this.reproduzindo = false;
        this.tempoAtual = 0;
    }

    // 2. Getters e setters
    public String getTitulo() {
        return titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public int getTempoAtual() {
        return tempoAtual;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public boolean isFavorita() {
        return favorita;
    }

    public boolean isReproduzindo() {
        return reproduzindo;
    }

    // 3. Favoritos
    public void marcarComoFavorita() {
        this.favorita = true;
    }

    public void desmarcarComoFavorita() {
        this.favorita = false;
    }

    // 4. Duração em formato usual
    public String getDuracaoUsual() {
        int minutos = duracao / 60;
        int segundos = duracao % 60;
        return minutos + "min " + segundos + "s";
    }

    // 5. Controle de reprodução (interface Reproduzivel)
    @Override
    public void reproduzir() {
        this.reproduzindo = true;
    }

    @Override
    public void pausar() {
        this.reproduzindo = false;
    }

    // 🔹 6. Controle de progresso (genérico)
    public void avancar(int segundos) {
        tempoAtual = Math.min(tempoAtual + segundos, duracao);
        // Garante que não ultrapasse a duração
    }

    public void retroceder(int segundos) {
        tempoAtual = Math.max(tempoAtual - segundos, 0);
        // Garante que não fique negativo 
    }





}
