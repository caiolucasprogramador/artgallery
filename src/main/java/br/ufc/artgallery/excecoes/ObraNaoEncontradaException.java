package br.ufc.artgallery.excecoes;

import br.ufc.artgallery.models.Obra;

public class ObraNaoEncontradaException extends Exception {
    public ObraNaoEncontradaException() {
        super("Obra não encontrada!");
    }
}
