package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PainelListar extends JPanel {
    private JTable tabelaObras;
    private DefaultTableModel modeloTabela;
    private JButton btnAtualizar = new JButton("Atualizar Lista");
    private ArtGallery gallery;

    public PainelListar(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel lblTitulo = new JLabel("Acervo de Obras de Arte Ativas", JLabel.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Título", "Autor", "Tipo", "Detalhes Específicos"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaObras = new JTable(modeloTabela);
        tabelaObras.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaObras);
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnAtualizar.setPreferredSize(new Dimension(140, 35));
        painelBotao.add(btnAtualizar);
        add(painelBotao, BorderLayout.SOUTH);

        // Primeira carga de dados
        atualizarTabela();

        btnAtualizar.addActionListener(e -> atualizarTabela());
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0);

        try {
            Vector<Obra> listaObras = gallery.listarObras();

            for (Obra obra : listaObras) {
                Object[] linha = new Object[5];
                linha[0] = obra.getId();
                linha[1] = obra.getTitulo();
                linha[2] = obra.getAutor();
                linha[3] = obra.getTipo();
                
                if (obra instanceof PinturaDigital) {
                    PinturaDigital p = (PinturaDigital) obra;
                    linha[4] = "Resolução: " + p.getResolucao() + " | Software: " + p.getSoftwareUtilizado();
                } else if (obra instanceof Modelagem3D) {
                    Modelagem3D m = (Modelagem3D) obra;
                    linha[4] = "Polígonos: " + m.getNumeroPoligonos() + " | Engine: " + m.getEngine();
                } else if (obra instanceof ArteGenerativa) {
                    ArteGenerativa a = (ArteGenerativa) obra;
                    linha[4] = "Algoritmo: " + a.getAlgoritmo() + " | Seed: " + a.getSeed();
                } else {
                    linha[4] = "";
                }

                modeloTabela.addRow(linha);
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar acervo: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}