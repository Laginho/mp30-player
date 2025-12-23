package br.ufc.poo.visao;
import javax.swing.JButton;

import br.ufc.poo.controle.PlayerController;

public class JanelaPrincipal {
    JButton btnPlay = new JButton("Play");
    JButton btnPause = new JButton("Pause");

PlayerController controller = new PlayerController();

btnPlay.addActionListener(e -> controller.reproduzir());
btnPause.addActionListener(e -> controller.pausar());

//Para continuar, teria que mexer no 'PlayerControle'(parte dos meu colegas)

}
