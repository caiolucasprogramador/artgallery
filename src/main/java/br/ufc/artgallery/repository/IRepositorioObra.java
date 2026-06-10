package br.ufc.artgallery.repository;

import br.ufc.artgallery.excecoes.ObraJaCadastradaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.Obra;

import java.sql.SQLException;
import java.util.Vector;

public interface IRepositorioObra {

    void cadastrar(Obra obra) throws ObraJaCadastradaException, SQLException;
    Vector<Obra> buscar(String titulo) throws SQLException, ObraNaoEncontradaException;
    Obra buscar(int id) throws SQLException, ObraNaoEncontradaException;

    void atualizar (Obra obra) throws ObraNaoEncontradaException, SQLException;

    void remover(int id) throws ObraNaoEncontradaException, SQLException;
    void desativarObra(int id) throws SQLException, ObraNaoEncontradaException;

    void ativarObra(int id) throws SQLException, ObraNaoEncontradaException;

    Vector<Obra> listar() throws SQLException;
}
