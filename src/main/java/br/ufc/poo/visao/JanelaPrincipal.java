package br.ufc.poo.visao;


import javax.swing.*;

import br.ufc.poo.controle.PlayerController;
import br.ufc.poo.modelo.Midia;

import java.awt.*;

public class JanelaPrincipal extends JFrame {

    private PlayerController controller;
    private TelaBiblioteca telaBiblioteca;

    public JanelaPrincipal() {
    //"Atributos" da classe são inicializados no construtor
    // para facilitar testes e reduzir acoplamento entre classes 
        controller = new PlayerController();
        telaBiblioteca = new TelaBiblioteca();


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
   


