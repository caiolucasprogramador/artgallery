package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.Exposicao;
import br.ufc.artgallery.dao.ExposicaoDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class PainelRemoverExposicao extends JPanel {
    private JComboBox<Exposicao> comboExposicoes = new JComboBox<>();
    private JButton btnRemover = new JButton("Remover Exposição");
    private ArtGallery gallery;

    public PainelRemoverExposicao(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(BorderFactory.createTitledBorder("Remover Exposição do Sistema"));

        JPanel painelInput = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelInput.add(new JLabel("Selecione a Exposição:"));
        comboExposicoes.setPreferredSize(new Dimension(250, 25));
        painelInput.add(comboExposicoes);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnRemover.setPreferredSize(new Dimension(160, 35));
        painelBotao.add(btnRemover);

        painelForm.add(painelInput);
        painelForm.add(painelBotao);

        add(painelForm);

        carregarExposicoes();

        btnRemover.addActionListener(e -> removerExposicao());
    }

    public void carregarExposicoes() {
        try {
            ExposicaoDAO expoDAO = new ExposicaoDAO();
            Vector<Exposicao> lista = expoDAO.listarTodas();

            comboExposicoes.removeAllItems();
            for (Exposicao expo : lista) {
                comboExposicoes.addItem(expo);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar exposições: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerExposicao() {
        Exposicao expoSelecionada = (Exposicao) comboExposicoes.getSelectedItem();

        if (expoSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Nenhuma exposição selecionada.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover a exposição \"" + expoSelecionada.getNome() + "\"?\nEsta ação não pode ser desfeita.",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ExposicaoDAO expoDAO = new ExposicaoDAO();
                expoDAO.removerExposicao(expoSelecionada.getId());

                JOptionPane.showMessageDialog(this, "Exposição removida com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarExposicoes();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover exposição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}