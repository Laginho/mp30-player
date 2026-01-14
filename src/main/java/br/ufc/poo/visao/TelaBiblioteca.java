package br.ufc.poo.visao;
import br.ufc.poo.controle.PlayerController;
import br.ufc.poo.controle.LeitorMetadados;
import br.ufc.poo.modelo.Midia;
import br.ufc.poo.modelo.Musica;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Arrays;


public class TelaBiblioteca extends JPanel {


    private DefaultListModel<Midia> model;
    private JList<Midia> listaMidias;
    DefaultListModel<Midia> modeloFila; 
    JList<Midia> listaFila; 
    // JList é só a parte visual, o DefaultListModel guarda os dados e manipula
    private JButton btnCarregarPasta;
    private JLabel labelStatus;
    private PlayerController controller;
    private JSlider sliderTempo;
    private JButton btnProxima;
    private JButton btnAnterior;
    private JButton btnVerFila; 
    private Timer timer; 
    private int segundosAtuais;
    private JLabel labelTempo;
    private JLabel labelFila;


    public TelaBiblioteca(PlayerController controller) {
        this.controller = controller;
        BorderLayout bl1 = new BorderLayout();
        this.setLayout(bl1);

      
        model = new DefaultListModel<>();
        listaMidias = new JList<>(model);
        listaMidias.setCellRenderer(new MidiaCellRenderer());

        listaMidias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        modeloFila = new DefaultListModel<>();
        listaFila = new JList<>(modeloFila);
     
        btnCarregarPasta = new JButton("Carregar pasta de músicas");
        btnCarregarPasta.addActionListener(e -> escolherPasta());
        // função delta evita criar uma classe anônima 

        
        labelStatus = new JLabel("Nenhuma pasta carregada");

        // Painel topo com botão e status
        JPanel topo = new JPanel(new BorderLayout());
        topo.add(btnCarregarPasta, BorderLayout.WEST);
        topo.add(labelStatus, BorderLayout.CENTER);

        // Botões Próxima / Anterior
        btnProxima = ComponentesCustomizados.criarBotao(">>");
        btnAnterior = ComponentesCustomizados.criarBotao("<<");
        
      btnProxima.addActionListener(e -> {
    controller.proxima();
    Midia atual = controller.getMidiaAtual();
    tocarMidia(atual);
});
btnAnterior.addActionListener(e -> {
    controller.anterior();
    Midia atual = controller.getMidiaAtual();
    tocarMidia(atual);
});

          
        JPanel painelControles = new JPanel();
        painelControles.add(btnAnterior);
        painelControles.add(btnProxima);
        

        
        JPanel painelNorte = new JPanel(new BorderLayout());
        painelNorte.add(topo, BorderLayout.NORTH);
        painelNorte.add(painelControles, BorderLayout.SOUTH);

        this.add(painelNorte, BorderLayout.NORTH);

        // Slider de tempo e label para mostrar tempo decorrido
        sliderTempo = ComponentesCustomizados.criarSliderTempo();
        labelTempo = new JLabel("00:00 / 00:00");
     labelTempo.setHorizontalAlignment(SwingConstants.CENTER);
 JPanel painelTempo = new JPanel(new BorderLayout());
    painelTempo.add(sliderTempo, BorderLayout.CENTER);
    painelTempo.add(labelTempo, BorderLayout.SOUTH);

this.add(painelTempo, BorderLayout.SOUTH);
        

// Lista de músicas no centro
        this.add(new JScrollPane(listaMidias), BorderLayout.CENTER);
       
// Adiciona mídia à fila de reprodução visualmente
// 1. Criando a interface gráfica em si 
        JScrollPane scrollFila= new JScrollPane(listaFila);
        scrollFila.setPreferredSize(new Dimension(150, 0));
        JPanel painelFila = new JPanel(new BorderLayout());
JLabel labelFila = new JLabel("Fila de Reprodução", SwingConstants.CENTER);
        painelFila.add(labelFila, BorderLayout.NORTH);
        painelFila.add(scrollFila, BorderLayout.CENTER);
        this.add(painelFila, BorderLayout.EAST);
//2. Adicionando a funcionalidade de adicionar mídias à fila de reprodução
    listaMidias.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        int index = listaMidias.locationToIndex(e.getPoint());
        if (index < 0) return;

        Rectangle cellBounds = listaMidias.getCellBounds(index, index);
        int xClick = e.getX();

        // Região do "+"
        int limitePlus = cellBounds.x + cellBounds.width - 30;

        if (xClick >= limitePlus) {
            Midia m = listaMidias.getModel().getElementAt(index);
            controller.adicionarNaFila(m);
            modeloFila.addElement(m);
        }
    }
});
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
        // mudar para um exception depois
        if (arquivos == null || arquivos.length == 0) {
            labelStatus.setText("Nenhum MP3 encontrado");
            JOptionPane.showMessageDialog(
                    this,
                    "Nenhum arquivo MP3 encontrado.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        Arrays.sort(arquivos);

        int musicasCarregadas = 0;

    for (File f : arquivos) {
        Musica musica = LeitorMetadados.lerMusica(f.getAbsolutePath()); 
        if (musica != null) {
            model.addElement(musica);
            this.controller.adicionarNaPlaylist(musica);
            musicasCarregadas++;
        }

    }

    labelStatus.setText(musicasCarregadas + " músicas carregadas");
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
    public void iniciarProgresso(Midia midia) {
    sliderTempo.setMinimum(0);
    sliderTempo.setMaximum(midia.getDuracao());
    sliderTempo.setValue(0);

    segundosAtuais = 0;

    if (timer != null) {
        timer.stop();
    }
     labelTempo.setText(
    formatarTempo(0) + " / " +
    formatarTempo(midia.getDuracao())
);
    timer = new Timer(1000, e -> {
    segundosAtuais++;
    sliderTempo.setValue(segundosAtuais);

    labelTempo.setText(
        formatarTempo(segundosAtuais) + " / " +
        formatarTempo(midia.getDuracao())
    );

    if (segundosAtuais >= midia.getDuracao()) {
        timer.stop();
    }
});


    timer.start();
}
    // Retorna a duração usual formatada mm:ss
    private String formatarTempo(int segundos) {
    int min = segundos / 60;
    int sec = segundos % 60;
    return String.format("%02d:%02d", min, sec);

}
public void tocarMidia(Midia midia) {
    // Esse método garante que o timer zere após avançar/voltar a música
    if (midia == null) return;

    // Para timer anterior
    if (timer != null && timer.isRunning()) {
        timer.stop();
    }

    segundosAtuais = 0;

    // Atualiza seleção visual da JList
    listaMidias.setSelectedValue(midia, true);

    // Reinicia progresso
    iniciarProgresso(midia);
}


} 


