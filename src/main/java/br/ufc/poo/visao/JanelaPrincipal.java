package br.ufc.poo.visao;

// import br.ufc.poo.controle.PlayerController;

import javax.swing.*;
import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private PlayerController controller;
    private TelaBiblioteca telaBiblioteca;

    public JanelaPrincipal() {
    //"Atributos" da classe são inicializados no construtor
    // para facilitar testes e reduzir acoplamento entre classes 
        telaBiblioteca = new TelaBiblioteca();
        controller = new PlayerController();


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

        // 🔹 Adiciona tudo na janela
        add(telaBiblioteca, BorderLayout.CENTER);
        add(painelControles, BorderLayout.SOUTH);

        setVisible(true);
    }
}
