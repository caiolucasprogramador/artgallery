package br.ufc.artgallery.repository;

import br.ufc.artgallery.dao.ArteGenerativaDAO;
import br.ufc.artgallery.dao.Modelagem3DDAO;
import br.ufc.artgallery.dao.ObraDAO;
import br.ufc.artgallery.dao.PinturaDigitalDAO;
import br.ufc.artgallery.excecoes.ObraJaCadastradaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.ArteGenerativa;
import br.ufc.artgallery.models.Modelagem3D;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.models.PinturaDigital;

import java.sql.SQLException;
import java.util.Vector;

public class RepositorioObraDB implements IRepositorioObra {
    @Override
    public void cadastrar(Obra obra) throws ObraJaCadastradaException, SQLException {
        ObraDAO obraDAO = new ObraDAO();
        if (obraDAO.existeObra(obra.getTitulo(), obra.getAutor())) {
            throw new ObraJaCadastradaException(obra);
        }

        int id_gerado = obraDAO.criarObra(obra);

        if (obra instanceof PinturaDigital) {
            PinturaDigitalDAO pinturaDigitalDAO = new PinturaDigitalDAO();
            PinturaDigital pinturaDigital = (PinturaDigital) obra;
            pinturaDigitalDAO.criar(id_gerado, pinturaDigital);
        }
        else if (obra instanceof Modelagem3D) {
            Modelagem3DDAO modelagem3DDAO = new Modelagem3DDAO();
            Modelagem3D modelagem3D = (Modelagem3D) obra;
            modelagem3DDAO.criar(id_gerado, modelagem3D);
        }
        else if (obra instanceof ArteGenerativa) {
            ArteGenerativaDAO arteGenerativaDAO = new ArteGenerativaDAO();
            ArteGenerativa arteGenerativa = (ArteGenerativa) obra;
            arteGenerativaDAO.criar(id_gerado, arteGenerativa);
        }
    }

    @Override
    public Obra buscar(int id) throws SQLException, ObraNaoEncontradaException {
        ObraDAO obraDAO = new ObraDAO();
        Obra obra = obraDAO.buscarObra(id);

        return obra;
    }

    public Vector<Obra> buscar(String titulo) throws SQLException, ObraNaoEncontradaException {
        ObraDAO obraDAO = new ObraDAO();
        Vector<Obra> obras_encontradas = obraDAO.buscarObra(titulo);

        return obras_encontradas;
    }

    @Override
    public void atualizar(Obra obra) throws ObraNaoEncontradaException, SQLException {
        ObraDAO obraDAO = new ObraDAO();
        if (!obraDAO.existeObra(obra.getTitulo(), obra.getAutor())) {
            throw new ObraNaoEncontradaException();
        }
        obraDAO.atualizarObra(obra);
        if (obra instanceof PinturaDigital) {
            PinturaDigitalDAO pdao = new PinturaDigitalDAO();
            pdao.atualizar((PinturaDigital) obra);
        }
        else if (obra instanceof Modelagem3D) {
            Modelagem3DDAO mdao = new Modelagem3DDAO();
            mdao.atualizar((Modelagem3D) obra);
        }
        else if (obra instanceof ArteGenerativa) {
            ArteGenerativaDAO adao = new ArteGenerativaDAO();
            adao.atualizar((ArteGenerativa) obra);
        }
    }

    @Override
    public void remover(int id) throws ObraNaoEncontradaException, SQLException {
        ObraDAO obraDAO = new ObraDAO();
        obraDAO.removerObra(id);
    }

    @Override
    public void desativarObra(int id) throws SQLException, ObraNaoEncontradaException {
        ObraDAO obraDAO = new ObraDAO();
        obraDAO.desativarObra(id);
    }

    @Override
    public void ativarObra(int id) throws SQLException, ObraNaoEncontradaException {
        ObraDAO obraDAO = new ObraDAO();
        obraDAO.ativarObra(id);
    }

    @Override
    public Vector<Obra> listar() throws SQLException {
        ObraDAO obraDAO = new ObraDAO();
        return obraDAO.buscarTodas();
    }
}
