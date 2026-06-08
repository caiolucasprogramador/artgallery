package br.ufc.artgallery.repository;

import br.ufc.artgallery.excecoes.ObraJaCadastradaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.Obra;

import java.util.Vector;

public interface IRepositorioObra {

    public void cadastrar(Obra obra) throws ObraJaCadastradaException;
    public Obra buscar(String titulo);
    public void atualizar (Obra obra) throws ObraNaoEncontradaException;
    public void remover(String titulo) throws ObraNaoEncontradaException;
    public Vector<Obra> listar();
}
