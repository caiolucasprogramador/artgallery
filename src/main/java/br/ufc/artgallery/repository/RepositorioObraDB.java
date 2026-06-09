package br.ufc.artgallery.repository;

import br.ufc.artgallery.excecoes.ObraJaCadastradaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.Obra;

import java.util.Vector;

public class RepositorioObraDB implements IRepositorioObra {
    @Override
    public void cadastrar(Obra obra) throws ObraJaCadastradaException {

    }

    @Override
    public Obra buscar(String titulo) {
        return null;
    }

    @Override
    public void atualizar(Obra obra) throws ObraNaoEncontradaException {

    }

    @Override
    public void remover(String titulo) throws ObraNaoEncontradaException {

    }

    @Override
    public Vector<Obra> listar() {
        return null;
    }
}
