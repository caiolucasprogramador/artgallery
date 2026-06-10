package br.ufc.artgallery;

import br.ufc.artgallery.excecoes.*;
import br.ufc.artgallery.models.Avaliacao;
import br.ufc.artgallery.models.Exposicao;
import br.ufc.artgallery.models.Obra;

import java.sql.SQLException;
import java.util.Vector;

public interface IArtGallery {
    void publicarObra(Obra obra) throws ObraJaCadastradaException, SQLException;
    void removerObra(int id) throws ObraNaoEncontradaException, SQLException;

    void desativarObra(int id) throws ObraNaoEncontradaException, SQLException;
    void ativarObra(int id) throws ObraNaoEncontradaException, SQLException;

    void avaliarObra(int idObra, Avaliacao avaliacao) throws ObraNaoEncontradaException, SQLException;
    Vector<Obra> listarObras() throws SQLException;

    Vector<Obra> buscarPorAutor(String autor) throws SQLException;


    void criarExposicao(Exposicao exposicao) throws SQLException;
    void adicionarObraEmExposicao(int idObra, int idExpo) throws SQLException;

    Vector<Obra> topObras() throws SQLException;
    Vector<Obra> obrasExpostas(String nomeExposicao) throws SQLException, NotaInvalidaException;
}