package br.ufc.artgallery.dao;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.excecoes.NotaInvalidaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;

import br.ufc.artgallery.models.*;

import java.sql.*;
import java.util.Vector;

public class ObraDAO {
    public Obra mapearObra(ResultSet rs) throws SQLException {
        String tipo = rs.getString("tipo");
        int id = rs.getInt("id");
        String titulo = rs.getString("titulo");
        String autor = rs.getString("autor");

        if (tipo.equals("PINTURA DIGITAL")) {
            return new PinturaDigital(id, titulo, autor, rs.getString("resolucao"), rs.getString("software_utilizado"));
        }
        else if (tipo.equals("MODELAGEM 3D")) {
            return new Modelagem3D(id, titulo, autor, rs.getInt("numero_poligonos"), rs.getString("engine"));
        }
        else if (tipo.equals("ARTE GENERATIVA")) {
            return new ArteGenerativa(id, titulo, autor, rs.getString("algoritmo"), rs.getLong("seed"));
        }
        else {
            throw new SQLException("Tipo não encontrado: " + tipo);
        }
    }

    public Obra buscarObra(int id) throws ObraNaoEncontradaException, SQLException {
        String sql = "SELECT o.*, p.resolucao, p.software_utilizado, " +
                "m.numero_poligonos, m.engine, a.algoritmo, a.seed FROM obras o " +
                "LEFT JOIN pintura_digital p ON p.id_obra = o.id " +
                "LEFT JOIN modelagem_3d m ON m.id_obra = o.id " +
                "LEFT JOIN arte_generativa a ON a.id_obra = o.id " +
                "WHERE o.id = ?";

        try (Connection conn = Conexao.getConexao();
            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery();) {
                if (rs.next()) {
                    Obra obra = mapearObra(rs);
                    AvaliacaoDAO avdao = new AvaliacaoDAO();
                    for (Avaliacao av : avdao.listarPorObra(id)) {
                        obra.adicionarAvaliacao(av);
                    }
                    return obra;
                }
            } catch (NotaInvalidaException e) {
                throw new SQLException("Nota inválida: " + e.getMessage());
            }
            throw new ObraNaoEncontradaException();
        }
    }
    public Vector<Obra> buscarTodas() throws SQLException {
        String sql = "SELECT o.*, p.resolucao, p.software_utilizado, " +
                "m.numero_poligonos, m.engine, a.algoritmo, a.seed FROM obras o " +
                "LEFT JOIN pintura_digital p ON p.id_obra = o.id " +
                "LEFT JOIN modelagem_3d m ON m.id_obra = o.id " +
                "LEFT JOIN arte_generativa a ON a.id_obra = o.id";
        Vector<Obra> obras = new Vector<Obra>();
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            try (ResultSet rs = ps.executeQuery();) {
                while (rs.next()) {
                    Obra obra = mapearObra(rs);
                    AvaliacaoDAO avdao = new AvaliacaoDAO();
                    for (Avaliacao av : avdao.listarPorObra(obra.getId())) {
                        obra.adicionarAvaliacao(av);
                    }
                    obras.add(obra);
                }
            } catch (NotaInvalidaException e) {
                throw new SQLException("Nota inválida: " + e.getMessage());
            }
            return obras;
        }
    }

    public int criarObra(Obra obra) throws SQLException {
        String sql = "INSERT INTO obras(titulo, autor, tipo) VALUES(?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        ps.setString(1, obra.getTitulo());
        ps.setString(2, obra.getAutor());
        ps.setString(3, obra.getTipo());
        ps.executeUpdate();

        ResultSet rs = ps.getGeneratedKeys();
        if (rs.next()) return rs.getInt("id");
        }
        return -1;
    }

    public void removerObra(int id) throws ObraNaoEncontradaException, SQLException {
        buscarObra(id);

        String sql = "UPDATE obras SET ativa = false WHERE id = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
