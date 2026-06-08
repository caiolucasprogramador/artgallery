package br.ufc.artgallery.models;

public class PinturaDigital extends Obra {
    private String resolucao;
    private String softwareUtilizado;

    public PinturaDigital(int id, String titulo, String autor, String resolucao, String softwareUtilizado) {
        super(id, titulo, autor, "PINTURA DIGITAL");
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }
    public PinturaDigital (String titulo, String autor, String resolucao, String softwareUtilizado) {
        super(titulo, autor, "PINTURA DIGITAL");
        this.resolucao = resolucao;
        this.softwareUtilizado = softwareUtilizado;
    }

    public String getResolucao() {
        return resolucao;
    }
    public String getSoftwareUtilizado() {
        return softwareUtilizado;
    }

    @Override
    public String exibirDetalhes() {
        return "Título: " + this.getTitulo() +
                "\nAutor: " + this.getAutor() +
                "\nTipo: " + this.getTipo() +
                "\nResolução: " + this.resolucao +
                "\nSoftware: " + this.softwareUtilizado;
    }
}
