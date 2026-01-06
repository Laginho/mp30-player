package br.ufc.poo.modelo;

public class Musica extends Midia {
    private String artista; 
    private String album;
    //1.Construtores 
    
    // Construtor completo 
    // Ex.: new Musica("Bohemian Rhapsody", 354, "Queen", "A Night at the Opera");
    public Musica(String titulo, int duracao, String artista, String album) {
        super(titulo, duracao);
        this.artista = artista;
        this.album = album;
    }
    // Construtor (para arquivos MP3)
    // Ex.: new Musica("bohemian_rhapsody.mp3", "/home/user/Musicas/bohemian_rhapsody.mp3");
      public Musica(String titulo, int duracao,String caminho) {
        super(titulo, duracao);  
        this.caminho = caminho;
        this.artista = "Desconhecido";
        this.album = "Desconhecido";
    }

    //2.Getters e Setters

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