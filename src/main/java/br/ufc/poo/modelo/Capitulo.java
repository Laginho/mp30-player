package br.ufc.poo.modelo;

public class Capitulo {
// Para deixar Audiobook mais completo, resolvi criar a classe Capitulo 
// Com essa classe, Audiobook vai ter métodos mais interessantes
private int numero;
    private String titulo;
    private int inicioEmSegundos;

    public Capitulo(int numero, String titulo, int inicioEmSegundos) {
        this.numero = numero;
        this.titulo = titulo;
        this.inicioEmSegundos = inicioEmSegundos;
    }

    public int getNumero() {
        return numero;
    }

    public int getInicioEmSegundos() {
        return inicioEmSegundos;
    }

    @Override
    public String toString() {
        return "Capítulo " + numero + " - " + titulo;
    }

}

