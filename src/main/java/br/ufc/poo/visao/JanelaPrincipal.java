package br.ufc.poo.visao;

import javax.swing.*;

import br.ufc.poo.controle.PlayerController;
import br.ufc.poo.controle.estrategias.ReproducaoAleatoria;
import br.ufc.poo.controle.estrategias.ReproducaoRepetir;
import br.ufc.poo.controle.estrategias.ReproducaoSequencial;
import br.ufc.poo.modelo.Midia;

import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private PlayerController controller;
    private TelaBiblioteca telaBiblioteca;

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

        // 🔹 Painel inferior (controles)
        JPanel painelControles = new JPanel(new FlowLayout());
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");

        painelControles.add(btnPlay);
        painelControles.add(btnPause);
        // 🔹 Ações dos botões
        btnPlay.addActionListener(e -> {
            Midia selecionada = telaBiblioteca.getMidiaSelecionada();

            if (selecionada != null) {
                controller.tocar(selecionada);
                telaBiblioteca.tocarMidia(selecionada);
            } else {
                // Não há seleção → deixa o controller decidir
                controller.proxima();
                Midia atual = controller.getMidiaAtual();
                telaBiblioteca.tocarMidia(atual);
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
