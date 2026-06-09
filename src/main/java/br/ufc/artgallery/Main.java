package br.ufc.artgallery;

import br.ufc.artgallery.conexao.Conexao;
import br.ufc.artgallery.dao.ObraDAO;
import br.ufc.artgallery.excecoes.NotaInvalidaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.models.PinturaDigital;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, ObraNaoEncontradaException, NotaInvalidaException {
        ObraDAO dao = new ObraDAO();
        Obra obra = new PinturaDigital("Meu amorzão lindo: Bianca", "Deus", "FULL 16K HD ULTRA MAX", "Barro e minha costela");
        PinturaDigital pd = (PinturaDigital) dao.buscarObra(1);
        System.out.println(pd.exibirDetalhes());
    }
}