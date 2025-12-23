package br.ufc.poo.modelo;

public class Musica extends Midia {
    private String artista; 
    private String album;
    //1.Construtor, getters e setters
    public Musica(String titulo, int duracao, String artista, String album) {
        super(titulo, duracao);
        this.artista = artista;
        this.album = album;
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
    
    
}       