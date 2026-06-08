package br.ufc.artgallery.models;

public class ArteGenerativa extends Obra {
    private String algoritmo;
    private long seed;

    public ArteGenerativa(int id, String titulo, String autor, String algoritmo, long seed) {
        super(id, titulo, autor, "ARTE GENERATIVA");
        this.algoritmo = algoritmo;
        this.seed = seed;
    }
    public ArteGenerativa(String titulo, String autor, String algoritmo, long seed) {
        super(titulo, autor, "ARTE GENERATIVA");
        this.algoritmo = algoritmo;
        this.seed = seed;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public long getSeed() {
        return seed;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + this.getTitulo() +
                "\nAutor: " + this.getAutor() +
                "\nTipo: " + this.getTipo() +
                "\nAlgoritmo: " + this.algoritmo +
                "\nSeed: " + this.seed;
    }
}
