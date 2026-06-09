package br.ufc.artgallery.dao;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.models.Modelagem3D;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Modelagem3DDAO {
    public void criar(int idObra, Modelagem3D m) throws SQLException {
        String sql = "INSERT INTO modelagem_3d(id_obra, numero_poligonos, engine) VALUES(?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObra);
            ps.setInt(2, m.getNumeroPoligonos());
            ps.setString(3, m.getEngine());

            ps.executeUpdate();
        }
    }
}
