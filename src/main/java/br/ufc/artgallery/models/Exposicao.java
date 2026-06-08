package br.ufc.artgallery.models;

import java.util.Vector;

public class Exposicao {
    private int id;
    private String nome;
    private Vector<Obra> obras;

    public Exposicao(int id, String nome) {
        this.id = id;
        this.nome = nome;
        obras = new Vector<Obra>();
    }

    public Exposicao(String nome) {
        this.nome = nome;
        obras = new Vector<Obra>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
    
    public void adicionarObra(Obra obra) {
        obras.add(obra);
    }

    public Vector<Obra> listarObras() {
        return obras;
    }
}
