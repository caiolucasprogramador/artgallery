package br.ufc.artgallery.dao;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.models.ArteGenerativa;
import br.ufc.artgallery.models.Modelagem3D;
import br.ufc.artgallery.models.PinturaDigital;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
public class ArteGenerativaDAO {
    public void criar(int idObra, ArteGenerativa a) throws SQLException {
        String sql = "INSERT INTO arte_generativa(id_obra, algoritmo, seed) VALUES(?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObra);
            ps.setString(2, a.getAlgoritmo());
            ps.setLong(3, a.getSeed());

            ps.executeUpdate();
        }
    }
    public void atualizar(ArteGenerativa a) throws SQLException {
        String sql = "UPDATE arte_generativa SET algoritmo = ?, seed = ? WHERE id_obra = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, a.getAlgoritmo());
            ps.setLong(2, a.getSeed());
            ps.setInt(3, a.getId());

            ps.executeUpdate();
        }
    }
}
