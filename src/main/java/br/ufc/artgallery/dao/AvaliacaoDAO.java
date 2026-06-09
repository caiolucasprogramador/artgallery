package br.ufc.artgallery.dao;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.excecoes.NotaInvalidaException;
import br.ufc.artgallery.models.Avaliacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class AvaliacaoDAO {
    public void criar(int idObra, Avaliacao a) throws SQLException {
        String sql = "INSERT INTO avaliacoes(id_obra, usuario, nota, comentario) VALUES(?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idObra);
            ps.setString(2, a.getUsuario());
            ps.setInt(3, a.getNota());
            ps.setString(4, a.getComentario());
            ps.executeUpdate();
        }
    }

    public Vector<Avaliacao> listarPorObra(int idObra) throws SQLException, NotaInvalidaException {
        String sql = "SELECT * FROM avaliacoes WHERE id_obra = ?";
        Vector<Avaliacao> avaliacoes = new Vector<Avaliacao>();
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1,idObra);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Avaliacao a = new Avaliacao(rs.getInt("id"), rs.getString("usuario"), rs.getInt("nota"), rs.getString("comentario"));
                    avaliacoes.add(a);
                }
            }
        }
        return avaliacoes;
    }
}
