package br.ufc.artgallery.excecoes;

public class ObraDesativadaException extends RuntimeException {
    public ObraDesativadaException() {
        super("Obra está desativada!");
    }
}
