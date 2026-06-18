package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.dao.ObraDAO;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class PainelBuscarPorTitulo extends JPanel {
    private JTextField txtTituloBusca = new JTextField(20);
    private JButton btnBuscar = new JButton("Buscar");
    private JTable tabelaResultados;
    private DefaultTableModel modeloTabela;
    private ArtGallery gallery;

    public PainelBuscarPorTitulo(ArtGallery gallery) {
        this.gallery = gallery;

        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelSuperior.setBorder(BorderFactory.createTitledBorder("Filtro de Busca"));
        painelSuperior.add(new JLabel("Título da Obra:"));
        painelSuperior.add(txtTituloBusca);
        painelSuperior.add(btnBuscar);
        add(painelSuperior, BorderLayout.NORTH);

        String[] colunas = {"ID", "Título", "Autor", "Tipo", "Status"};
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
        txtTituloBusca.addActionListener(e -> executarBusca());
    }

    private void executarBusca() {
        String termoBusca = txtTituloBusca.getText().trim();
        modeloTabela.setRowCount(0);

        if (termoBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, digite um título para buscar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ObraDAO dao = new ObraDAO();
            Vector<Obra> resultados = dao.buscarObra(termoBusca);

            if (resultados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nenhuma obra encontrada com esse título.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            for (Obra obra : resultados) {
                Object[] linha = new Object[5];
                linha[0] = obra.getId();
                linha[1] = obra.getTitulo();
                linha[2] = obra.getAutor();
                linha[3] = obra.getTipo();
                linha[4] = obra.isAtiva() ? "Ativa" : "Desativada";
                modeloTabela.addRow(linha);
            }

            modeloTabela.fireTableDataChanged();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar busca: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void limparTela() {
        txtTituloBusca.setText("");
        modeloTabela.setRowCount(0);
        txtTituloBusca.requestFocus();
    }
}