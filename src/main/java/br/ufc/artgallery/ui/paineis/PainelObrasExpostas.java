package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PainelObrasExpostas extends JPanel {
    private JTextField txtNomeExposicao = new JTextField(20);
    private JButton btnBuscar = new JButton("Buscar Obras");
    private JTable tabelaObras;
    private DefaultTableModel modeloTabela;
    private ArtGallery gallery;

    public PainelObrasExpostas(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        painelFiltro.setBorder(BorderFactory.createTitledBorder("Consultar Exposição"));
        painelFiltro.add(new JLabel("Nome da Exposição:"));
        painelFiltro.add(txtNomeExposicao);

        btnBuscar.setPreferredSize(new Dimension(130, 25));
        painelFiltro.add(btnBuscar);

        add(painelFiltro, BorderLayout.NORTH);


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


        btnBuscar.addActionListener(e -> buscarObras());


        txtNomeExposicao.addActionListener(e -> buscarObras());
    }

    private void buscarObras() {
        String nomeExposicao = txtNomeExposicao.getText().trim();

        if (nomeExposicao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite o nome da exposição.", "Campo Vazio", JOptionPane.WARNING_MESSAGE);
            return;
        }


        modeloTabela.setRowCount(0);

        try {
            Vector<Obra> obrasExpostas = gallery.obrasExpostas(nomeExposicao);

            if (obrasExpostas == null || obrasExpostas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma obra encontrada ou exposição inexistente.", "Sem Resultados", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Obra obra : obrasExpostas) {
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

        } catch (NullPointerException ex) {

            JOptionPane.showMessageDialog(this, "A exposição \"" + nomeExposicao + "\" não foi encontrada no banco.", "Não Encontrada", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar obras expostas: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}