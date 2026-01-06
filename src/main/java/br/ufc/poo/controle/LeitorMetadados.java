package br.ufc.poo.controle;

import br.ufc.poo.modelo.Musica;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import java.io.File;

public class LeitorMetadados {

    public static Musica lerMusica(String caminhoArquivo) {
        try {
            // Carrega o arquivo usando a biblioteca mp3agic
            Mp3File mp3file = new Mp3File(caminhoArquivo);

            // Pega a duração em segundos (automático!)
            int duracao = (int) mp3file.getLengthInSeconds();

            String titulo = "Desconhecido";
            String artista = "Desconhecido";
            String album = "Desconhecido";

            // Prioridade 1: Tenta ler Tags ID3v2 (mais modernas)
            if (mp3file.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3file.getId3v2Tag();
                titulo = id3v2Tag.getTitle();
                artista = id3v2Tag.getArtist();
                album = id3v2Tag.getAlbum();
            }
            // Prioridade 2: Tenta ler Tags ID3v1 (mais antigas)
            else if (mp3file.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3file.getId3v1Tag();
                titulo = id3v1Tag.getTitle();
                artista = id3v1Tag.getArtist();
                album = id3v1Tag.getAlbum();
            }

            // Tratamento: Se não tiver título na tag, usa o nome do arquivo
            if (titulo == null || titulo.isEmpty()) {
                File arquivo = new File(caminhoArquivo);
                titulo = arquivo.getName().replace(".mp3", "");
            }

            // Retorna a música prontinha
            return new Musica(titulo, duracao, artista, album, caminhoArquivo);

        } catch (Exception e) {
            System.err.println("Erro ao ler metadados de: " + caminhoArquivo);
            e.printStackTrace();
            return null;
        }
    }
}