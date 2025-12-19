package br.ufc.poo.modelo;

import br.ufc.poo.modelo.interfaces.Reproduzivel;

public abstract class Midia implements Reproduzivel {
    private String titulo;
    private int duracao; // em segundos
    private boolean favorita; 
    private boolean reproduzindo;
    // 1.Construtor, getters e setters
    public Midia(String titulo, int duracao) {
    this.titulo = titulo;
    this.duracao = duracao;
    this.favorita = false;
    this.reproduzindo = false;
}
    public String getTitulo() {
        return titulo;
    }
    public int getDuracao() {
        return duracao;
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
    public void marcarComoFavorita() {
        this.favorita = true;
    }
    public void desmarcarComoFavorita() {
        this.favorita = false;
    }
    public boolean isReproduzindo() {
        return reproduzindo;
    }   
    //2.Método que converta duração para formato usual (200s --> 3min 20s, por exemplo)
    public String getDuracaoUsual() {
        int minutos = duracao / 60;
        int segundos = duracao % 60;
        return minutos + "min " + segundos + "s";
    }
    //3.Implementação dos métodos da interface Reproduzivel
    @Override
    public void reproduzir() {
      this.reproduzindo = true;  
    }
    @Override
    public void pausar() {
      this.reproduzindo = false;
      }

}
