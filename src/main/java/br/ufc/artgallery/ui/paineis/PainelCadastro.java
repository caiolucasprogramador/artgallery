package br.ufc.artgallery.ui.paineis;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.models.ArteGenerativa;
import br.ufc.artgallery.models.Modelagem3D;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.models.PinturaDigital;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class PainelCadastro extends JPanel {
    private JTextField titulo = new JTextField(15);
    private JTextField autor = new JTextField(15);

    private JTextField resolucao = new JTextField(15);
    private JTextField softwareUtilizado = new JTextField(15);

    private JTextField numeroPoligonos = new JTextField(15);
    private JTextField engine = new JTextField(15);

    private JTextField algoritmo = new JTextField(15);
    private JTextField seed = new JTextField(15);

    private JComboBox<String> comboBox = new JComboBox<>(new String[]{
            "PINTURA DIGITAL", "MODELAGEM 3D", "ARTE GENERATIVA"
    });

    public PainelCadastro(ArtGallery gallery) {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel painelBase = new JPanel(new GridLayout(3, 2, 10, 10));
        painelBase.setBorder(BorderFactory.createTitledBorder("Informações Gerais"));
        painelBase.add(new JLabel("Título:"));
        painelBase.add(titulo);
        painelBase.add(new JLabel("Autor:"));
        painelBase.add(autor);
        painelBase.add(new JLabel("Tipo de Arte:"));
        painelBase.add(comboBox);

        CardLayout cardLayout = new CardLayout();
        JPanel painelEspecifico = new JPanel(cardLayout);
        painelEspecifico.setBorder(BorderFactory.createTitledBorder("Detalhes Específicos"));

        JPanel pnlPintura = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlPintura.add(new JLabel("Resolução:"));
        pnlPintura.add(resolucao);
        pnlPintura.add(new JLabel("Software:"));
        pnlPintura.add(softwareUtilizado);

        JPanel pnlModelagem = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlModelagem.add(new JLabel("Número de Polígonos:"));
        pnlModelagem.add(numeroPoligonos);
        pnlModelagem.add(new JLabel("Engine:"));
        pnlModelagem.add(engine);

        JPanel pnlArteGen = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlArteGen.add(new JLabel("Algoritmo:"));
        pnlArteGen.add(algoritmo);
        pnlArteGen.add(new JLabel("Seed:"));
        pnlArteGen.add(seed);

        painelEspecifico.add(pnlPintura, "PINTURA DIGITAL");
        painelEspecifico.add(pnlModelagem, "MODELAGEM 3D");
        painelEspecifico.add(pnlArteGen, "ARTE GENERATIVA");

        comboBox.addActionListener(e -> {
            String tipoSelecionado = (String) comboBox.getSelectedItem();
            cardLayout.show(painelEspecifico, tipoSelecionado);
        });


        JPanel painelCentro = new JPanel();
        painelCentro.setLayout(new BoxLayout(painelCentro, BoxLayout.Y_AXIS));
        painelCentro.add(painelBase);
        painelCentro.add(Box.createVerticalStrut(15));
        painelCentro.add(painelEspecifico);

        JButton btnCadastrar = new JButton("Cadastrar Obra");
        btnCadastrar.setPreferredSize(new Dimension(150, 40));
        JPanel painelBotao = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotao.add(btnCadastrar);

        btnCadastrar.addActionListener(e -> {
            try {
                String tipo = comboBox.getSelectedItem().toString();
                Obra obra = switch (tipo) {
                    case "PINTURA DIGITAL" -> new PinturaDigital(titulo.getText(), autor.getText(), resolucao.getText(), softwareUtilizado.getText());
                    case "MODELAGEM 3D" -> new Modelagem3D(titulo.getText(), autor.getText(), Integer.parseInt(numeroPoligonos.getText()), engine.getText());
                    case "ARTE GENERATIVA" -> new ArteGenerativa(titulo.getText(), autor.getText(), algoritmo.getText(), Long.parseLong(seed.getText()));
                    default -> throw new IllegalArgumentException("Tipo inválido");
                };
                gallery.publicarObra(obra);
                JOptionPane.showMessageDialog(this, "Obra cadastrada com sucesso!");
                limparCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Erro: Insira valores numéricos válidos nos campos de dados específicos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            }
        });

        add(painelCentro, BorderLayout.CENTER);
        add(painelBotao, BorderLayout.SOUTH);
    }

    private void limparCampos() {
        titulo.setText("");
        autor.setText("");
        resolucao.setText("");
        softwareUtilizado.setText("");
        numeroPoligonos.setText("");
        engine.setText("");
        algoritmo.setText("");
        seed.setText("");
        titulo.requestFocus();
    }
}