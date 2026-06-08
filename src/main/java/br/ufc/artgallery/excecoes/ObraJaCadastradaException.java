package br.ufc.artgallery.excecoes;

import br.ufc.artgallery.models.Obra;

public class ObraJaCadastradaException extends Exception {
    private Obra obra;
    public ObraJaCadastradaException(Obra obra) {
        super("Obra já cadastrada!"); this.obra = obra;
    }
    public Obra getObra() {
        return obra;
    }
}
