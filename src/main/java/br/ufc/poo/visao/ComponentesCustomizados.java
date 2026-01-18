package br.ufc.poo.visao;

import javax.swing.JButton;
import javax.swing.JSlider;

public class ComponentesCustomizados {
    
    //Nessa classe, vamos adicionar componentes para "encorpoar" o MP3
    
    //Criando um slider que mostra o tempo da música
    public static JSlider criarSliderTempo() {
        JSlider slider = new JSlider(0, 100, 0);
        slider.setPaintTicks(true);
        slider.setPaintLabels(false);
        slider.setFocusable(false);
        slider.setBorder(null);
        slider.setToolTipText("0:00");
        slider.addChangeListener(e -> {
            int segundos = slider.getValue();
            int minutos = segundos / 60;
            int segs = segundos % 60;
            slider.setToolTipText(String.format("%d:%02d", minutos, segs));
        });
        return slider;
    }
  public static JButton criarBotao(String texto) {
        return new JButton(texto);
    // Caso a gente queira, futuramente, esse método permite criar botões customizados
    } 
}
