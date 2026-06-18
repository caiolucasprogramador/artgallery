package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.Exposicao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelCriarExposicao extends JPanel {
    private JTextField txtNome = new JTextField(20);
    private JButton btnCriar = new JButton("Criar Exposição");
    private ArtGallery gallery;

    public PainelCriarExposicao(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(BorderFactory.createTitledBorder("Nova Exposição"));

        JPanel painelInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelInput.add(new JLabel("Nome da Exposição:"));
        painelInput.add(txtNome);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnCriar.setPreferredSize(new Dimension(140, 35));
        painelBotao.add(btnCriar);

        painelForm.add(painelInput);
        painelForm.add(painelBotao);

        add(painelForm);

        btnCriar.addActionListener(e -> salvarExposicao());

        txtNome.addActionListener(e -> salvarExposicao());
    }

    private void salvarExposicao() {
        String nome = txtNome.getText().trim();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha o nome da exposição.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Exposicao novaExposicao = new Exposicao(nome);

            gallery.criarExposicao(novaExposicao);

            JOptionPane.showMessageDialog(this, "Exposição \"" + nome + "\" criada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            txtNome.setText("");
            txtNome.requestFocus();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar exposição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}