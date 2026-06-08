package br.ufc.artgallery.models;

import br.ufc.artgallery.excecoes.NotaInvalidaException;

public class Avaliacao {
    private int id;
    private String usuario;
    private int nota;
    private String comentario;


    public Avaliacao(int id, String usuario, int nota, String comentario) throws NotaInvalidaException {
        if (nota < 0 || nota > 10) {
            throw new NotaInvalidaException(nota);
        }

        this.id = id;
        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
    }


    public Avaliacao(String usuario, int nota, String comentario) throws NotaInvalidaException {
        if (nota < 0 || nota > 10) {
            throw  new NotaInvalidaException(nota);
        }
        this.usuario = usuario;
        this.nota = nota;
        this.comentario = comentario;
    }

    public int getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }
}
