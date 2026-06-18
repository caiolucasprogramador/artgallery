package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PainelTopObras extends JPanel {
    private JTable tabelaTop;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar = new JButton("Atualizar Ranking");
    private ArtGallery gallery;

    public PainelTopObras(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("🏆 Top Obras - Melhores Avaliadas 🏆", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(184, 134, 11));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"Posição", "Título", "Autor", "Tipo", "Média Nota"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaTop = new JTable(modeloTabela);
        tabelaTop.getTableHeader().setReorderingAllowed(false);

        tabelaTop.getColumnModel().getColumn(0).setPreferredWidth(60);
        tabelaTop.getColumnModel().getColumn(0).setMaxWidth(80);

        JScrollPane scrollPane = new JScrollPane(tabelaTop);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAtualizar.setPreferredSize(new Dimension(150, 35));
        painelBotao.add(btnAtualizar);
        add(painelBotao, BorderLayout.SOUTH);

        atualizarRanking();

        btnAtualizar.addActionListener(e -> atualizarRanking());
    }

    public void atualizarRanking() {
        modeloTabela.setRowCount(0);

        try {
            Vector<Obra> listaTop = gallery.topObras();

            int posicao = 1;
            for (Obra obra : listaTop) {
                Object[] linha = new Object[5];

                linha[0] = posicao + "º";
                linha[1] = obra.getTitulo();
                linha[2] = obra.getAutor();
                linha[3] = obra.getTipo();

                double media = obra.mediaAvaliacoes();

                linha[4] = String.format("%.1f", media);

                modeloTabela.addRow(linha);
                posicao++;
            }

            if (listaTop.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma obra avaliada até o momento.", "Ranking Vazio", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar o ranking de obras: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}