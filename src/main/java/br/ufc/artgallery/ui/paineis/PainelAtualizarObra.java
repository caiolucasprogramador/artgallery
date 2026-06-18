package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Vector;

public class PainelAtualizarObra extends JPanel {
    private JComboBox<Obra> comboObras = new JComboBox<>();
    private JButton btnCarregar = new JButton("Editar Obra");
    private JButton btnAtualizarLista = new JButton("Atualizar");

    private JTextField txtTitulo = new JTextField(20);
    private JTextField txtAutor = new JTextField(20);

    private JPanel painelEspecifico = new JPanel(new CardLayout());
    private CardLayout clEspecifico;

    private JTextField txtResolucao = new JTextField(15);
    private JTextField txtSoftware = new JTextField(15);

    private JTextField txtPoligonos = new JTextField(15);
    private JTextField txtEngine = new JTextField(15);

    private JTextField txtAlgoritmo = new JTextField(15);
    private JTextField txtSeed = new JTextField(15);

    private JButton btnSalvar = new JButton("Salvar Alterações");
    private ArtGallery gallery;
    private Obra obraEmEdicao;

    public PainelAtualizarObra(ArtGallery gallery) {
        this.gallery = gallery;
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelSelecao = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        painelSelecao.setBorder(BorderFactory.createTitledBorder("Selecione a Obra para Modificar"));
        comboObras.setPreferredSize(new Dimension(300, 25));
        painelSelecao.add(comboObras);
        painelSelecao.add(btnAtualizarLista);
        painelSelecao.add(btnCarregar);
        add(painelSelecao, BorderLayout.NORTH);

        JPanel painelFormPrincipal = new JPanel();
        painelFormPrincipal.setLayout(new BoxLayout(painelFormPrincipal, BoxLayout.Y_AXIS));
        painelFormPrincipal.setBorder(BorderFactory.createTitledBorder("Modificar Atributos"));

        JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        p1.add(new JLabel("Título:")); p1.add(txtTitulo);

        JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        p2.add(new JLabel("Autor:"));  p2.add(txtAutor);

        painelFormPrincipal.add(p1);
        painelFormPrincipal.add(p2);

        clEspecifico = (CardLayout) painelEspecifico.getLayout();

        JPanel cardPintura = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        cardPintura.setBorder(BorderFactory.createTitledBorder("Detalhes de Pintura Digital"));
        cardPintura.add(new JLabel("Resolução:")); cardPintura.add(txtResolucao);
        cardPintura.add(new JLabel("Software:")); cardPintura.add(txtSoftware);
        painelEspecifico.add(cardPintura, "PINTURA DIGITAL");

        JPanel card3D = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        card3D.setBorder(BorderFactory.createTitledBorder("Detalhes de Modelagem 3D"));
        card3D.add(new JLabel("Polígonos:")); card3D.add(txtPoligonos);
        card3D.add(new JLabel("Engine:")); card3D.add(txtEngine);
        painelEspecifico.add(card3D, "MODELAGEM 3D");

        JPanel cardGenerativa = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        cardGenerativa.setBorder(BorderFactory.createTitledBorder("Detalhes de Arte Generativa"));
        cardGenerativa.add(new JLabel("Algoritmo:")); cardGenerativa.add(txtAlgoritmo);
        cardGenerativa.add(new JLabel("Seed:")); cardGenerativa.add(txtSeed);
        painelEspecifico.add(cardGenerativa, "ARTE GENERATIVA");

        painelFormPrincipal.add(painelEspecifico);

        JPanel painelSalvar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnSalvar.setPreferredSize(new Dimension(160, 35));
        btnSalvar.setEnabled(false);
        painelSalvar.add(btnSalvar);
        painelFormPrincipal.add(painelSalvar);

        add(painelFormPrincipal, BorderLayout.CENTER);

        carregarObrasDoBanco();
        btnAtualizarLista.addActionListener(e -> carregarObrasDoBanco());
        btnCarregar.addActionListener(e -> prepararEdicao());
        btnSalvar.addActionListener(e -> salvarAlteracoes());
    }

    public void carregarObrasDoBanco() {
        try {
            comboObras.setEnabled(false);
            Vector<Obra> lista = gallery.listarObras();
            comboObras.removeAllItems();
            for (Obra o : lista) {
                comboObras.addItem(o);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao popular obras: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
            comboObras.setEnabled(true);
        }
    }

    private void prepararEdicao() {
        obraEmEdicao = (Obra) comboObras.getSelectedItem();
        if (obraEmEdicao == null) {
            JOptionPane.showMessageDialog(this, "Selecione uma obra válida.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        txtTitulo.setText(obraEmEdicao.getTitulo());
        txtAutor.setText(obraEmEdicao.getAutor());

        if (obraEmEdicao instanceof PinturaDigital) {
            PinturaDigital pd = (PinturaDigital) obraEmEdicao;
            txtResolucao.setText(pd.getResolucao());
            txtSoftware.setText(pd.getSoftwareUtilizado());
            clEspecifico.show(painelEspecifico, "PINTURA DIGITAL");
        } else if (obraEmEdicao instanceof Modelagem3D) {
            Modelagem3D m3d = (Modelagem3D) obraEmEdicao;
            txtPoligonos.setText(String.valueOf(m3d.getNumeroPoligonos()));
            txtEngine.setText(m3d.getEngine());
            clEspecifico.show(painelEspecifico, "MODELAGEM 3D");
        } else if (obraEmEdicao instanceof ArteGenerativa) {
            ArteGenerativa ag = (ArteGenerativa) obraEmEdicao;
            txtAlgoritmo.setText(ag.getAlgoritmo());
            txtSeed.setText(String.valueOf(ag.getSeed()));
            clEspecifico.show(painelEspecifico, "ARTE GENERATIVA");
        }

        btnSalvar.setEnabled(true);
    }

    private void salvarAlteracoes() {
        if (obraEmEdicao == null) return;

        try {
            String titulo = txtTitulo.getText().trim();
            String autor = txtAutor.getText().trim();
            int id = obraEmEdicao.getId();
            boolean ativa = obraEmEdicao.isAtiva();

            Obra novaObra = null;

            if (obraEmEdicao instanceof PinturaDigital) {
                String resolucao = txtResolucao.getText().trim();
                String software = txtSoftware.getText().trim();

                novaObra = new PinturaDigital(id, titulo, autor, resolucao, software);
            }
            else if (obraEmEdicao instanceof Modelagem3D) {
                int poligonos = Integer.parseInt(txtPoligonos.getText().trim());
                String engine = txtEngine.getText().trim();

                novaObra = new Modelagem3D(id, titulo, autor, poligonos, engine);
            }
            else if (obraEmEdicao instanceof ArteGenerativa) {
                String algoritmo = txtAlgoritmo.getText().trim();
                long seed = Long.parseLong(txtSeed.getText().trim());

                novaObra = new ArteGenerativa(id, titulo, autor, algoritmo, seed);
            }
            gallery.atualizarObra(novaObra);

            JOptionPane.showMessageDialog(this, "Obra atualizada com sucesso no banco de dados!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            carregarObrasDoBanco();
            btnSalvar.setEnabled(false);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Formato numérico inválido em Polígonos ou Seed.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Falha ao atualizar obra: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}