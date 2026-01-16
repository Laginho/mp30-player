package br.ufc.poo.visao;

import javax.swing.*;

import br.ufc.poo.controle.PlayerController;
import br.ufc.poo.controle.estrategias.ReproducaoAleatoria;
import br.ufc.poo.controle.estrategias.ReproducaoRepetir;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.modelo.Midia;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

public class JanelaPrincipal extends JFrame {

    private PlayerController controller;
    private TelaBiblioteca telaBiblioteca;

    public JanelaPrincipal() {
        controller = new PlayerController();
        telaBiblioteca = new TelaBiblioteca(controller);
        controller.setTela(telaBiblioteca);
        this.setTitle("MP30 Player");
        this.setSize(600, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // Painel inferior
        JPanel painelControles = new JPanel(new FlowLayout());
        JButton btnPlay = new JButton("Play");
        JButton btnStop = new JButton("Stop");

        painelControles.add(btnPlay);
        painelControles.add(btnStop);

        // Botão Play
        btnPlay.addActionListener(e -> {
             controller.proxima();
            telaBiblioteca.tocarMidia(controller.getMidiaAtual());
        });

        // Botão Stop
        btnStop.addActionListener(e -> {
            Midia atual = controller.getMidiaAtual();
            if (atual != null) {
                atual.parar();
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

        add(telaBiblioteca, BorderLayout.CENTER);
        add(painelControles, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        new JanelaPrincipal();
    }

}
