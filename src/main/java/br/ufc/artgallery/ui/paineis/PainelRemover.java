package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelRemover extends JPanel {

    private JTextField id = new JTextField(10);
    private JButton btnRemover = new JButton("Remover Obra");

    public PainelRemover(ArtGallery gallery) {
        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(BorderFactory.createTitledBorder("Excluir Obra do Acervo"));

        JPanel painelInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelInput.add(new JLabel("Id da Obra:"));
        painelInput.add(id);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnRemover.setPreferredSize(new Dimension(130, 35));
        painelBotao.add(btnRemover);

        painelForm.add(painelInput);
        painelForm.add(painelBotao);

        add(painelForm);

        btnRemover.addActionListener(e -> {
            try {
                String textoId = id.getText().trim();

                if (textoId.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, digite um ID.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int Id = Integer.parseInt(textoId);

                gallery.removerObra(Id);

                JOptionPane.showMessageDialog(this, "Operação de remoção concluída!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                id.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "O ID precisa ser um número inteiro válido.", "Erro de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao tentar remover: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}