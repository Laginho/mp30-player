package br.ufc.poo.controle;

import br.ufc.poo.modelo.Audio;
import br.ufc.poo.modelo.Midia;
import br.ufc.poo.modelo.Musica;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;

import java.io.File;

/**
 * Classe utilitária para leitura de metadados de arquivos MP3.
 * Utiliza a biblioteca mp3agic para extrair informações como
 * título, artista, álbum e duração dos arquivos.
 * 
 * @author Bruno Lage
 * @version 1.0
 */
public class LeitorMetadados {

    /**
     * Lê os metadados de um arquivo MP3 e cria o objeto de mídia apropriado.
     * <p>
     * Retorna {@link Musica} para arquivos com até 10 minutos,
     * ou {@link Audio} para arquivos mais longos.
     * </p>
     * 
     * @param caminhoArquivo o caminho completo do arquivo MP3
     * @return uma instância de {@link Midia} (Musica ou Audio), ou null se houver
     *         erro
     */
    public static Midia lerMidia(String caminhoArquivo) {
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
                if (id3v2Tag.getArtist() != null)
                    artista = id3v2Tag.getArtist();
                else
                    artista = id3v2Tag.getAlbumArtist();
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

            if (duracao > 600) {
                return new Audio(titulo, duracao, caminhoArquivo, artista);
            }
            return new Musica(titulo, duracao, caminhoArquivo, artista, album);

        } catch (Exception e) {
            System.err.println("Erro ao ler metadados de: " + caminhoArquivo);
            e.printStackTrace();
            return null;
        }
    }
}