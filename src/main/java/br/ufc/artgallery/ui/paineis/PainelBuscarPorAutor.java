package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PainelBuscarPorAutor extends JPanel {
    private JTextField txtAutor = new JTextField(20);
    private JButton btnBuscar = new JButton("Buscar");
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private ArtGallery gallery;

    public PainelBuscarPorAutor(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelBusca.setBorder(BorderFactory.createTitledBorder("Pesquisar Obras"));
        painelBusca.add(new JLabel("Nome do Autor:"));
        painelBusca.add(txtAutor);

        btnBuscar.setPreferredSize(new Dimension(100, 25));
        painelBusca.add(btnBuscar);
        add(painelBusca, BorderLayout.NORTH);

        String[] colunas = {"ID", "Título", "Tipo", "Status", "Detalhes Específicos"};

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabelaResultados = new JTable(modeloTabela);
        tabelaResultados.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(tabelaResultados);
        add(scrollPane, BorderLayout.CENTER);

        btnBuscar.addActionListener(e -> executarBusca());

        txtAutor.addActionListener(e -> executarBusca());
    }

    private void executarBusca() {
        String autor = txtAutor.getText().trim();

        if (autor.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o nome de um autor para pesquisar.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }


        modeloTabela.setRowCount(0);

        try {
            Vector<Obra> obrasEncontradas = gallery.buscarPorAutor(autor);

            if (obrasEncontradas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma obra encontrada para o autor: " + autor, "Sem Resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Obra obra : obrasEncontradas) {
                Object[] linha = new Object[5];
                linha[0] = obra.getId();
                linha[1] = obra.getTitulo();
                linha[2] = obra.getTipo();
                linha[3] = obra.isAtiva() ? "Ativa" : "Desativada";

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
            JOptionPane.showMessageDialog(this, "Erro ao realizar busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}