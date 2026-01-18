package br.ufc.poo.visao;

import javax.swing.JButton;
import javax.swing.JSlider;

/**
 * Classe utilitária com componentes visuais customizados.
 * Fornece botões e outros elementos com estilo padronizado.
 * 
 * @author Bruno Lage
 * @version 1.0
 */
public class ComponentesCustomizados {
    public static JSlider criarSliderTempo() {
        JSlider slider = new JSlider(0, 100, 0);
        slider.setPaintTicks(true);
        slider.setPaintLabels(false);
        return slider;
    }

    public static JButton criarBotao(String texto) {
        return new JButton(texto);
        // Caso a gente queira, futuramente, esse método permite criar botões
        // customizados
    }
}
