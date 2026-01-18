package br.ufc.poo.visao;

import javax.swing.*;
import java.awt.*;
import br.ufc.poo.modelo.Midia;

/**
 * Renderizador customizado para células da lista de mídias.
 * Exibe informações formatadas de cada mídia na biblioteca.
 * 
 * <p>
 * Informações exibidas:
 * </p>
 * <ul>
 * <li>Tipo da mídia (ícone)</li>
 * <li>Nome/título</li>
 * <li>Duração formatada</li>
 * </ul>
 * 
 * @author Bruno Lage
 * @version 1.0
 * @see TelaBiblioteca
 */
public class MidiaCellRenderer extends JPanel implements ListCellRenderer<Midia> {

    private JLabel lblNome;
    private JLabel lblAdd;
    private JLabel labelTipo;

    public MidiaCellRenderer() {
        lblNome = new JLabel();
        lblAdd = new JLabel("+");
        labelTipo = new JLabel();
        setLayout(new BorderLayout(10, 0));

        lblAdd.setFont(new Font("Arial", Font.BOLD, 18));
        lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdd.setPreferredSize(new Dimension(30, 30));
        add(labelTipo, BorderLayout.WEST);
        add(lblNome, BorderLayout.CENTER);
        add(lblAdd, BorderLayout.EAST);

    }

    @Override
    public Component getListCellRendererComponent(
            JList<? extends Midia> list,
            Midia value,
            int index,
            boolean isSelected,
            boolean cellHasFocus) {
        if (value.isAudio()) {
            labelTipo.setText("🎧 Áudio");
            labelTipo.setForeground(new Color(60, 90, 180));
            // Colocando uma distinção de cor para ficar "bonitinho"
        } else {
            labelTipo.setText("🎵 Música");
            labelTipo.setForeground(new Color(30, 150, 30));
        }

        lblNome.setText(value.toString());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        setOpaque(true);
        return this;

    }
}
