package br.ufc.artgallery.dao;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.models.ArteGenerativa;
import br.ufc.artgallery.models.PinturaDigital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PinturaDigitalDAO {
    public void criar(int idObra, PinturaDigital pd) throws SQLException {
        String sql = "INSERT INTO pintura_digital(id_obra, resolucao, software_utilizado) VALUES(?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObra);
            ps.setString(2, pd.getResolucao());
            ps.setString(3, pd.getSoftwareUtilizado());
            ps.executeUpdate();
        }
    }
    public void atualizar(PinturaDigital p) throws SQLException {
        String sql = "UPDATE pintura_digital SET resolucao = ?, software_utilizado = ? WHERE id_obra = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getResolucao());
            ps.setString(2, p.getSoftwareUtilizado());
            ps.setInt(3, p.getId());

            ps.executeUpdate();
        }
    }
}
