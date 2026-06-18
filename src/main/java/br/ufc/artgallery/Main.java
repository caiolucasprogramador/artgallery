package br.ufc.artgallery;

import br.ufc.artgallery.repository.RepositorioObraDB;
import br.ufc.artgallery.ui.MainFrame;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            try {
                RepositorioObraDB repo = new RepositorioObraDB();
                ArtGallery gallery = new ArtGallery(repo);
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                MainFrame frame = new MainFrame(gallery);
                frame.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro: " + e.getMessage());
            }
        });

    }

}