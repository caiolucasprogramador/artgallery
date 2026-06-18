package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.*;
import br.ufc.artgallery.dao.ObraDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PainelAtivarDesativarObra extends JPanel {
    private JTable tabelaObras;
    private DefaultTableModel modeloTabela;
    private JButton btnAlternarStatus = new JButton("Alternar Status");
    private JButton btnAtualizar = new JButton("🔄 Atualizar"); // Adicionado o botão físico de atualização
    private ArtGallery gallery;

    public PainelAtivarDesativarObra(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Gerenciar Status das Obras (Ativar / Desativar)", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Título", "Autor", "Tipo", "Status"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaObras = new JTable(modeloTabela);
        tabelaObras.getTableHeader().setReorderingAllowed(false);
        tabelaObras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(tabelaObras);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnAtualizar.setPreferredSize(new Dimension(130, 35));
        btnAlternarStatus.setPreferredSize(new Dimension(160, 35));
        painelBotao.add(btnAtualizar);
        painelBotao.add(btnAlternarStatus);
        add(painelBotao, BorderLayout.SOUTH);

        atualizarTabela();

        tabelaObras.getSelectionModel().addListSelectionListener(e -> {
            int linhaSelecionada = tabelaObras.getSelectedRow();
            if (linhaSelecionada != -1) {
                String status = (String) modeloTabela.getValueAt(linhaSelecionada, 4);
                if (status.equals("Ativa")) {
                    btnAlternarStatus.setText("Desativar Obra");
                } else {
                    btnAlternarStatus.setText("Ativar Obra");
                }
            }
        });

        btnAlternarStatus.addActionListener(e -> alternarStatusObra());

        btnAtualizar.addActionListener(e -> {
            atualizarTabela();
            JOptionPane.showMessageDialog(this, "Tabela de obras sincronizada com o banco!", "Informação", JOptionPane.INFORMATION_MESSAGE);
        });
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
        btnAlternarStatus.setText("Alternar Status");

        try {
            ObraDAO dao = new ObraDAO();
            Vector<Obra> listaObras = dao.buscarTodas();

            for (Obra obra : listaObras) {
                Object[] linha = new Object[5];
                linha[0] = obra.getId();
                linha[1] = obra.getTitulo();
                linha[2] = obra.getAutor();
                linha[3] = obra.getTipo();
                linha[4] = obra.isAtiva() ? "Ativa" : "Desativada";

                modeloTabela.addRow(linha);
            }

            // Força o componente visual do Swing a reconhecer que os dados mudaram e renderizar novamente
            modeloTabela.fireTableDataChanged();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar obras: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void alternarStatusObra() {
        int linhaSelecionada = tabelaObras.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma obra na tabela primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id = (int) modeloTabela.getValueAt(linhaSelecionada, 0);
        String titulo = (String) modeloTabela.getValueAt(linhaSelecionada, 1);
        String statusAtual = (String) modeloTabela.getValueAt(linhaSelecionada, 4);

        String acao = statusAtual.equals("Ativa") ? "desativar" : "ativar";

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente " + acao + " a obra \"" + titulo + "\"?",
                "Confirmar Mudança de Status",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (confirmacao == JOptionPane.YES_OPTION) {
            try {
                if (statusAtual.equals("Ativa")) {
                    gallery.desativarObra(id);
                    JOptionPane.showMessageDialog(this, "Obra desativada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    gallery.ativarObra(id);
                    JOptionPane.showMessageDialog(this, "Obra ativada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                }

                atualizarTabela();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro ao alterar status da obra: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}