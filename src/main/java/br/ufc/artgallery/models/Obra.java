package br.ufc.artgallery.models;

import java.util.Vector;

public abstract class Obra {
    private int id;
    private String titulo;
    private String autor;
    private String tipo;
    private boolean ativa;
    private Vector<Avaliacao> avaliacoes;

    public Obra (int id, String titulo, String autor, String tipo) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.tipo = tipo;
        this.ativa = true;
        this.avaliacoes = new Vector<Avaliacao>();
    }
    public Obra (String titulo, String autor, String tipo) {
        this.titulo = titulo;
        this.autor = autor;
        this.tipo = tipo;
        this.ativa = true;
        this.avaliacoes = new Vector<Avaliacao>();
    }

    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getTipo() {
        return tipo;
    }

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public Vector<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao) {
        if (avaliacao != null) {
            avaliacoes.add(avaliacao);
        }
        else {
            System.out.println("A avaliação não pode ser nula!");
        }
    }

    public double mediaAvaliacoes() {
        if (avaliacoes.isEmpty()) return 0;
        double soma = 0;
        for (int i = 0; i < avaliacoes.size(); ++i) {
            soma += avaliacoes.get(i).getNota();
        }
        return soma / avaliacoes.size();
    }

    public abstract String exibirDetalhes();
}
