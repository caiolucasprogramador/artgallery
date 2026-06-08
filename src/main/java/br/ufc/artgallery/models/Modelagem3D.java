package br.ufc.artgallery.models;

public class Modelagem3D extends Obra {
    private int numeroPoligonos;
    private String engine;

    public Modelagem3D(int id, String titulo, String autor, int numeroPoligonos, String engine) {
        super(id, titulo, autor, "MODELAGEM 3D");
        this.numeroPoligonos = numeroPoligonos;
        this.engine = engine;
    }
    public Modelagem3D(String titulo, String autor, int numeroPoligonos, String engine) {
        super(titulo, autor, "MODELAGEM 3D");
        this.numeroPoligonos = numeroPoligonos;
        this.engine = engine;
    }

    public int getNumeroPoligonos() {
        return numeroPoligonos;
    }

    public String getEngine() {
        return engine;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + this.getTitulo() +
                "\nAutor: " + this.getAutor() +
                "\nTipo: " + this.getTipo() +
                "\nPoligonos: " + this.numeroPoligonos +
                "\nEngine: " + this.engine;
    }
}
