package br.ufc.poo.controle;

import br.ufc.poo.modelo.Musica;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import java.io.File;

public class LeitorMetadados {

    public static Musica lerMusica(String caminhoArquivo) {
        try {
            Mp3File mp3file = new Mp3File(caminhoArquivo);
            int duracao = (int) mp3file.getLengthInSeconds();

            String titulo = "Desconhecido";
            String artista = "Desconhecido";
            String album = "Desconhecido";

            // Aqui, testamos dois tipos de tags ID3. Se nenhuma delas existir,
            // usamos de título o nome do arquivo, e, de resto, "Desconhecido".
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                titulo = id3v2Tag.getTitle();
                artista = id3v2Tag.getArtist();
                album = id3v2Tag.getAlbum();

            } else if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                titulo = id3v1Tag.getTitle();
                artista = id3v1Tag.getArtist();
                album = id3v1Tag.getAlbum();
            }

            if (titulo == null || titulo.isEmpty()) {
                File arquivo = new File(caminhoArquivo);
                titulo = arquivo.getName().replace(".mp3", "");
            }

            return new Musica(titulo, duracao, artista, album, caminhoArquivo);

        } catch (Exception e) {
            System.err.println("Erro ao ler metadados de: " + caminhoArquivo);
            e.printStackTrace();
            return null;
        }
    }
}