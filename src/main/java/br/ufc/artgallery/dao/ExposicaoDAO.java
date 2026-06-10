package br.ufc.artgallery.dao;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.excecoes.NotaInvalidaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.Avaliacao;
import br.ufc.artgallery.models.Exposicao;
import br.ufc.artgallery.dao.ObraDAO;
import br.ufc.artgallery.models.Obra;

import java.sql.*;
import java.util.List;
import java.util.Vector;

public class ExposicaoDAO {
    public Exposicao mapearExposicao(ResultSet rs) throws SQLException {
        return new Exposicao(rs.getInt("id"), rs.getString("nome"));
    }

    public int criarExposicao(Exposicao expo) throws SQLException {
        String sql = "INSERT INTO exposicoes(nome) VALUES(?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, expo.getNome());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt("id");
            }
            return -1;
        }
    }

    public Exposicao buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM exposicoes WHERE nome = ?";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapearExposicao(rs);
            }
        }
        return null;
    }

    public boolean adicionarObra(int id_obra, int id_expo) throws SQLException {
        String sql = "INSERT INTO exposicao_obra(id_obra, id_exposicao) VALUES(?, ?)";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_obra);
            ps.setInt(2, id_expo);

            return ps.executeUpdate() == 1;
        }
    }

    public boolean removeObra(int id_obra, int id_expo) throws SQLException {
        String sql = "DELETE FROM exposicao_obra WHERE id_obra = ? AND id_exposicao = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id_obra);
            ps.setInt(2, id_expo);

            return ps.executeUpdate() == 1;
        }
    }

    public Vector<Obra> listarObras(int id_expo) throws SQLException, NotaInvalidaException {
        String sql = "SELECT o.*, p.resolucao, p.software_utilizado, " +
                "m.numero_poligonos, m.engine, a.algoritmo, a.seed FROM exposicao_obra eo " +
                "JOIN obras o ON eo.id_obra = o.id " +
                "LEFT JOIN pintura_digital p ON p.id_obra = o.id " +
                "LEFT JOIN modelagem_3d m ON m.id_obra = o.id " +
                "LEFT JOIN arte_generativa a ON a.id_obra = o.id " +
                "WHERE eo.id_exposicao = ?";

        Vector<Obra> obras = new Vector<Obra>();
        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id_expo);
            try (ResultSet rs = ps.executeQuery()) {
                ObraDAO obraDAO = new ObraDAO();
                while (rs.next()) {
                    Obra obra = obraDAO.mapearObra(rs);
                    AvaliacaoDAO avdao = new AvaliacaoDAO();
                    for (Avaliacao av : avdao.listarPorObra(rs.getInt("id"))) {
                        obra.adicionarAvaliacao(av);
                    }
                    obras.add(obra);
                }
            }
        }
        return obras;
    }
}
