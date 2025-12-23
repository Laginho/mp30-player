package br.ufc.poo.modelo;

public class Audiobook extends Midia {
    private String autor; 

   public Audiobook(String titulo, int duracao, String autor) {
        super(titulo, duracao);
        this.autor = autor;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }   


}
