package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.Exposicao;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.dao.ExposicaoDAO;
import br.ufc.artgallery.dao.ObraDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class PainelAdicionarObraExposicao extends JPanel {
    private JComboBox<Exposicao> comboExposicoes = new JComboBox<>();
    private JComboBox<Obra> comboObras = new JComboBox<>();
    private JButton btnVincular = new JButton("Vincular Obra");
    private JButton btnAtualizar = new JButton("🔄 Atualizar Listas"); // Novo botão
    private ArtGallery gallery;

    public PainelAdicionarObraExposicao(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(BorderFactory.createTitledBorder("Adicionar Obra em uma Exposição"));

        JPanel painelExpo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelExpo.add(new JLabel("Selecione a Exposição:"));
        comboExposicoes.setPreferredSize(new Dimension(250, 25));
        painelExpo.add(comboExposicoes);

        JPanel painelObra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelObra.add(new JLabel("Selecione a Obra:"));
        comboObras.setPreferredSize(new Dimension(250, 25));
        painelObra.add(comboObras);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAtualizar.setPreferredSize(new Dimension(140, 35));
        btnVincular.setPreferredSize(new Dimension(140, 35));
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnVincular);

        painelForm.add(painelExpo);
        painelForm.add(painelObra);
        painelForm.add(painelBotoes);

        add(painelForm);

        carregarDados();

        btnVincular.addActionListener(e -> vincularObra());
        btnAtualizar.addActionListener(e -> {
            carregarDados();
            JOptionPane.showMessageDialog(this, "Listas atualizadas com sucesso!", "Informação", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void carregarDados() {
        try {
            comboExposicoes.setEnabled(false);
            comboObras.setEnabled(false);

            ExposicaoDAO expoDAO = new ExposicaoDAO();
            Vector<Exposicao> listaExposicoes = expoDAO.listarTodas();
            comboExposicoes.removeAllItems();
            for (Exposicao expo : listaExposicoes) {
                comboExposicoes.addItem(expo);
            }

            ObraDAO obraDAO = new ObraDAO();
            Vector<Obra> listaObras = obraDAO.buscarTodas();
            comboObras.removeAllItems();
            for (Obra o : listaObras) {
                if (o.isAtiva()) {
                    comboObras.addItem(o);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do banco: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            comboExposicoes.setEnabled(true);
            comboObras.setEnabled(true);
        }
    }

    private void vincularObra() {
        Exposicao expo = (Exposicao) comboExposicoes.getSelectedItem();
        Obra obra = (Obra) comboObras.getSelectedItem();

        if (expo == null || obra == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma exposição e uma obra válidas.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ExposicaoDAO expoDAO = new ExposicaoDAO();
            boolean sucesso = expoDAO.adicionarObra(obra.getId(), expo.getId());

            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Obra vinculada à exposição com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível vincular a obra.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (java.sql.SQLException ex) {
            if ("23505".equals(ex.getSQLState())) {
                JOptionPane.showMessageDialog(this, "Esta obra já está incluída nesta exposição!", "Aviso", JOptionPane.WARNING_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erro no banco de dados: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao vincular: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}