package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.Avaliacao;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.excecoes.ObraDesativadaException;
import br.ufc.artgallery.dao.ObraDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class PainelAvaliar extends JPanel {
    private JComboBox<Obra> comboObras = new JComboBox<>();
    private JTextField txtUsuario = new JTextField(15);
    private JTextField txtComentario = new JTextField(20);
    private JSlider sliderNota = new JSlider(0, 10, 5);
    private JButton btnAvaliar = new JButton("Enviar Avaliação");
    private JButton btnAtualizar = new JButton("🔄 Atualizar Lista"); // Novo botão

    private ArtGallery gallery;

    public PainelAvaliar(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new GridBagLayout());
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelForm = new JPanel();
        painelForm.setLayout(new BoxLayout(painelForm, BoxLayout.Y_AXIS));
        painelForm.setBorder(BorderFactory.createTitledBorder("Avaliar Obra"));

        JPanel painelObra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelObra.add(new JLabel("Selecione a Obra:"));
        comboObras.setPreferredSize(new Dimension(200, 25));
        painelObra.add(comboObras);

        JPanel painelUsuario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelUsuario.add(new JLabel("Usuário:"));
        painelUsuario.add(txtUsuario);

        JPanel painelNota = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        sliderNota.setMajorTickSpacing(2);
        sliderNota.setMinorTickSpacing(1);
        sliderNota.setPaintTicks(true);
        sliderNota.setPaintLabels(true);
        painelNota.add(new JLabel("Nota:"));
        painelNota.add(sliderNota);

        JPanel painelComentario = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelComentario.add(new JLabel("Comentário:"));
        painelComentario.add(txtComentario);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnAtualizar.setPreferredSize(new Dimension(140, 35));
        btnAvaliar.setPreferredSize(new Dimension(140, 35));
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAvaliar);

        painelForm.add(painelObra);
        painelForm.add(painelUsuario);
        painelForm.add(painelNota);
        painelForm.add(painelComentario);
        painelForm.add(painelBotoes);

        add(painelForm);

        carregarObras();

        btnAvaliar.addActionListener(e -> {
            try {
                Obra obraSelecionada = (Obra) comboObras.getSelectedItem();
                String usuario = txtUsuario.getText().trim();
                String comentario = txtComentario.getText().trim();

                if (obraSelecionada == null) {
                    JOptionPane.showMessageDialog(this, "Nenhuma obra selecionada ou disponível.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (usuario.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "O campo Usuário é obrigatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int idObra = obraSelecionada.getId();
                int nota = sliderNota.getValue();

                Avaliacao avaliacao = new Avaliacao(usuario, nota, comentario);
                gallery.avaliarObra(idObra, avaliacao);

                JOptionPane.showMessageDialog(this, "Avaliação salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();

            } catch (ObraNaoEncontradaException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Obra não encontrada.", "Erro", JOptionPane.ERROR_MESSAGE);
            } catch (ObraDesativadaException ex) {
                JOptionPane.showMessageDialog(this, "Esta obra está desativada e não pode receber novas avaliações.", "Aviso", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro no sistema ou banco de dados: " + ex.getMessage(), "Erro Crítico", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            carregarObras();
            JOptionPane.showMessageDialog(this, "Lista de obras atualizada!", "Informação", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void carregarObras() {
        try {
            comboObras.setEnabled(false);

            ObraDAO dao = new ObraDAO();
            Vector<Obra> listaObras = dao.buscarTodas();

            comboObras.removeAllItems();
            for (Obra o : listaObras) {
                if (o.isAtiva()) {
                    comboObras.addItem(o);
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar lista de obras: " + ex.getMessage(), "Erro de Carregamento", JOptionPane.ERROR_MESSAGE);
        } finally {
            comboObras.setEnabled(true);
        }
    }

    private void limparCampos() {
        txtUsuario.setText("");
        txtComentario.setText("");
        sliderNota.setValue(5);
        if (comboObras.getItemCount() > 0) {
            comboObras.setSelectedIndex(0);
        }
        txtUsuario.requestFocus();
    }
}