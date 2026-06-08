package br.ufc.artgallery.excecoes;

public class NotaInvalidaException extends Exception {
    private int nota;
    public NotaInvalidaException(int nota) {
        System.out.println("Nota inválida!");
        this.nota = nota;
    }

    public int getNota() {
        return nota;
    }
}
