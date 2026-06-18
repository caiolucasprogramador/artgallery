package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.Exposicao;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.dao.ExposicaoDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class PainelRemoverObraExposicao extends JPanel {
    private JComboBox<String> comboExposicoes = new JComboBox<>();
    private JComboBox<Obra> comboObras = new JComboBox<>();
    private JButton btnRemover = new JButton("Remover da Exposição");
    private ArtGallery gallery;

    public PainelRemoverObraExposicao(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(BorderFactory.createTitledBorder("Remover Obra de uma Exposição"));

        JPanel painelExpo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelExpo.add(new JLabel("Exposição:"));
        comboExposicoes.setPreferredSize(new Dimension(250, 25));
        painelExpo.add(comboExposicoes);

        JPanel painelObra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelObra.add(new JLabel("Obra Vinculada:"));
        comboObras.setPreferredSize(new Dimension(250, 25));
        painelObra.add(comboObras);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnRemover.setPreferredSize(new Dimension(180, 35));
        painelBotao.add(btnRemover);

        painelForm.add(painelExpo);
        painelForm.add(painelObra);
        painelForm.add(painelBotao);

        add(painelForm);

        carregarExposicoes();

        comboExposicoes.addActionListener(e -> carregarObrasDaExposicao());
        btnRemover.addActionListener(e -> removerObra());
    }

    public void carregarExposicoes() {
        try {
            ExposicaoDAO expoDAO = new ExposicaoDAO();
            Vector<Exposicao> lista = expoDAO.listarTodas();

            comboExposicoes.removeAllItems();
            for (Exposicao expo : lista) {
                comboExposicoes.addItem(expo.getNome());
            }
            if (comboExposicoes.getItemCount() > 0) {
                carregarObrasDaExposicao();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar exposições: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void carregarObrasDaExposicao() {
        String nomeExposicao = (String) comboExposicoes.getSelectedItem();
        comboObras.removeAllItems();

        if (nomeExposicao == null || nomeExposicao.isEmpty()) {
            return;
        }

        try {
            ExposicaoDAO expoDAO = new ExposicaoDAO();
            Exposicao expo = expoDAO.buscarPorNome(nomeExposicao);

            if (expo != null) {
                Vector<Obra> obras = expoDAO.listarObras(expo.getId());
                for (Obra o : obras) {
                    comboObras.addItem(o);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar obras da exposição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerObra() {
        String nomeExposicao = (String) comboExposicoes.getSelectedItem();
        Obra obraSelecionada = (Obra) comboObras.getSelectedItem();

        if (nomeExposicao == null || obraSelecionada == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma exposição e uma obra válidas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente remover a obra \"" + obraSelecionada.getTitulo() + "\" da exposição \"" + nomeExposicao + "\"?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                ExposicaoDAO expoDAO = new ExposicaoDAO();
                Exposicao expo = expoDAO.buscarPorNome(nomeExposicao);

                if (expo != null) {
                    boolean sucesso = expoDAO.removeObra(obraSelecionada.getId(), expo.getId());

                    if (sucesso) {
                        JOptionPane.showMessageDialog(this, "Obra removida da exposição com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        carregarObrasDaExposicao();
                    } else {
                        JOptionPane.showMessageDialog(this, "Não foi possível remover o vínculo da obra.", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao remover obra da exposição: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}