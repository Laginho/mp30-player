package br.ufc.poo.visao;
import javax.swing.*;
import java.awt.*; 
import br.ufc.poo.modelo.Midia;

// A criação dessa classe vai ajudar na 
// funcionalidade de adicionar mídias à fila de reprodução
public class MidiaCellRenderer extends JPanel implements ListCellRenderer<Midia> {

    private JLabel lblNome = new JLabel();
    private JLabel lblAdd = new JLabel("+");

    public MidiaCellRenderer() {
        setLayout(new BorderLayout(10, 0));

        lblAdd.setFont(new Font("Arial", Font.BOLD, 18));
        lblAdd.setHorizontalAlignment(SwingConstants.CENTER);
        lblAdd.setPreferredSize(new Dimension(30, 30));

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
