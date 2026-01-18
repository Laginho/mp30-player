package br.ufc.poo.visao;

import br.ufc.poo.controle.PlayerController;
import br.ufc.poo.controle.estrategias.ReproducaoAleatoria;
import br.ufc.poo.controle.estrategias.ReproducaoRepetir;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.modelo.Midia;
import java.awt.*;
import javax.swing.*;

public class JanelaPrincipal extends JFrame {

    private PlayerController controller;
    private TelaBiblioteca telaBiblioteca;
    private boolean tocandoMusica = false; 
    //Controla 

    public JanelaPrincipal() {
        // "Atributos" da classe são inicializados no construtor
        // para facilitar testes e reduzir acoplamento entre classes
        controller = new PlayerController();
        telaBiblioteca = new TelaBiblioteca(controller);

        this.setTitle("MP30 Player");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // 🔹 Painel inferior (controles) + Estilização dos botões
        JPanel painelControles = new JPanel(new FlowLayout());
        JButton btnPlayPause = new JButton("▶ Play");
        btnPlayPause.setFont(btnPlayPause.getFont().deriveFont(18f));
        btnPlayPause.setBackground(new Color(70, 130, 180));
        btnPlayPause.setForeground(Color.WHITE);

        painelControles.add(btnPlayPause);

        // 🔹 Ações dos botões
        btnPlayPause.addActionListener(e -> {
            Midia midiaNoPlayer = controller.getMidiaAtual();
            Midia selecionada = telaBiblioteca.getMidiaSelecionada();
            if (!tocandoMusica) {
                // Quando não estiver tocando, ele toca
                if (selecionada != null) {
                    controller.tocar(selecionada);
                    if(midiaNoPlayer == null || !midiaNoPlayer.equals(selecionada)){
                        telaBiblioteca.tocarMidia(selecionada);
                    } else {
                        telaBiblioteca.retomarTimer();
                    }
                } else if(midiaNoPlayer != null){
                    controller.tocar(midiaNoPlayer);
                    telaBiblioteca.retomarTimer();
                } else {
                    controller.proxima();
                    telaBiblioteca.tocarMidia(controller.getMidiaAtual());
                }
                btnPlayPause.setText("⏸ Pause");
                tocandoMusica = true;
            } else {
                // Quando estiver tocando, ele pausa
                controller.pausar();
                telaBiblioteca.pausarTimer();
                btnPlayPause.setText("▶ Play");
                tocandoMusica = false;
            }
        });
        // Permite que o usuário escolha o modo de reprodução
        String[] modosReproducao = {
            "Sequencial", "Aleatório", "Repetir"
        };
        JComboBox<String> comboModo = new JComboBox<>(modosReproducao);
        painelControles.add(comboModo);

        comboModo.addActionListener(e -> {
            String modo = (String) comboModo.getSelectedItem();

            switch (modo) {
                case "Sequencial":
                    controller.setEstrategia(new ReproducaoSequencial());
                    break;

                case "Aleatório":
                    controller.setEstrategia(new ReproducaoAleatoria());
                    break;

                case "Repetir":
                    controller.setEstrategia(new ReproducaoRepetir());
                    break;
            }
        });

        // 🔹 Adiciona tudo na janela

        add(telaBiblioteca, BorderLayout.CENTER);
        add(painelControles, BorderLayout.SOUTH);

        setVisible(true);

    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }

}
