package br.ufc.poo.visao;

import br.ufc.poo.modelo.Midia;
import br.ufc.poo.modelo.Musica;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Arrays;

public class TelaBiblioteca extends JPanel {

    private DefaultListModel<Midia> model;
    // DefaultListModel, tipo próprio do Swing, facilita manipulação da JList
    private JList<Midia> listaMidias;

    private JButton btnCarregarPasta;
    private JLabel labelStatus;

    public TelaBiblioteca() {
        BorderLayout bl1 = new BorderLayout();
        this.setLayout(bl1);
        //Inicializão dos "atributos" mais uma vez acontece no construtor, pelo mesmo motivo
        model = new DefaultListModel<>();
        listaMidias = new JList<>(model); 
        listaMidias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 
        // evita seleção múltipla de mídia  

        btnCarregarPasta = new JButton("Carregar pasta de músicas");
        btnCarregarPasta.addActionListener(e -> escolherPasta());

        labelStatus = new JLabel("Nenhuma pasta carregada");
        //Bom para feedback inicial ao usuário
        
        BorderLayout bl2 = new BorderLayout();
        JPanel topo = new JPanel(bl2);
        topo.add(btnCarregarPasta, bl2.WEST);
        topo.add(labelStatus, bl2.CENTER);

        this.add(topo, bl1.NORTH);
        this.add(new JScrollPane(listaMidias),bl1.CENTER);
    }

    // 🔹 Seleção de diretório
    private void escolherPasta() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        // Apenas pastas podem ser selecionada. Fica mais fácil para o usuário

        int resultado = chooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File pasta = chooser.getSelectedFile();
            carregarMidiasDaPasta(pasta);
        }
    }

    // 🔹 Leitura de MP3 reais
    private void carregarMidiasDaPasta(File pasta) {
        model.clear();

        File[] arquivos = pasta.listFiles((dir, nome) ->
                nome.toLowerCase().endsWith(".mp3")
        );

        if (arquivos == null || arquivos.length == 0) {
            lblStatus.setText("Nenhum MP3 encontrado");
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum arquivo MP3 encontrado.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Arrays.sort(arquivos);

        for (File f : arquivos) {
            Musica musica = new Musica( f.getName(),0,f.getAbsolutePath() );
            model.addElement(musica);
            //duração é inicializada no construtor como '0' por enquanto
            // e pode ser calculada depois 
        }

        labelStatus.setText(arquivos.length + " músicas carregadas");
    }

    // 🔹 Interface usada pela JanelaPrincipal 
    public Midia getMidiaSelecionada() {
        return listaMidias.getSelectedValue();
    }

    public boolean temSelecao() {
        return listaMidias.getSelectedIndex() != -1;
    }

    public void limpar() {
        model.clear();
        labelStatus.setText("Biblioteca limpa");
    }
}


