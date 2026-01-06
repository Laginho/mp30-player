package br.ufc.poo.visao;

// import br.ufc.poo.controle.PlayerController;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    // private PlayerController controller;
    private TelaBiblioteca telaBiblioteca;

    public JanelaPrincipal() {
        // controller = new PlayerController();
        telaBiblioteca = new TelaBiblioteca();

        setTitle("MP30 Player");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());

        // 🔹 Painel inferior (controles)
        JPanel painelControles = new JPanel(new FlowLayout());
        JButton btnPlay = new JButton("Play");
        JButton btnPause = new JButton("Pause");

        painelControles.add(btnPlay);
        painelControles.add(btnPause);

        // 🔹 Adiciona tudo na janela
        add(telaBiblioteca, BorderLayout.CENTER);
        add(painelControles, BorderLayout.SOUTH);

        setVisible(true);
    }
}
