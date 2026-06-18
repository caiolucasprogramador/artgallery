package br.ufc.artgallery;

import br.ufc.artgallery.dao.AvaliacaoDAO;
import br.ufc.artgallery.dao.ExposicaoDAO;
import br.ufc.artgallery.dao.ObraDAO;
import br.ufc.artgallery.excecoes.NotaInvalidaException;
import br.ufc.artgallery.excecoes.ObraDesativadaException;
import br.ufc.artgallery.excecoes.ObraJaCadastradaException;
import br.ufc.artgallery.excecoes.ObraNaoEncontradaException;
import br.ufc.artgallery.models.Avaliacao;
import br.ufc.artgallery.models.Exposicao;
import br.ufc.artgallery.models.Obra;
import br.ufc.artgallery.repository.IRepositorioObra;

import java.sql.SQLException;
import java.util.Vector;

public class ArtGallery implements IArtGallery {
    IRepositorioObra repositorio;

    public ArtGallery(IRepositorioObra repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public void publicarObra(Obra obra) throws ObraJaCadastradaException, SQLException {
        repositorio.cadastrar(obra);
    }

    @Override
    public void removerObra(int id) throws ObraNaoEncontradaException, SQLException {
        repositorio.remover(id);
    }

    @Override
    public void desativarObra(int id) throws ObraNaoEncontradaException, SQLException {
        repositorio.desativarObra(id);
    }

    @Override
    public void ativarObra(int id) throws ObraNaoEncontradaException, SQLException {
        repositorio.ativarObra(id);
    }

    @Override
    public void avaliarObra(int idObra, Avaliacao avaliacao) throws ObraNaoEncontradaException, SQLException, ObraDesativadaException {
        Obra obra =  repositorio.buscar(idObra);
        if (!obra.isAtiva()) {
            throw new ObraDesativadaException();
        }
        AvaliacaoDAO adao = new AvaliacaoDAO();
        adao.criar(idObra, avaliacao);
    }

    @Override
    public Vector<Obra> listarObras() throws SQLException {
        Vector<Obra> obras_encontradas = repositorio.listar();
        Vector<Obra> obras_ativas = new Vector<Obra>();
        for (Obra obra : obras_encontradas) {
            if (obra.isAtiva()) {
                obras_ativas.add(obra);
            }
        }
        return obras_ativas;
    }

    @Override
    public Vector<Obra> buscarPorAutor(String autor) throws SQLException {
        ObraDAO obraDAO = new ObraDAO();
        return obraDAO.buscarObraPorAutor(autor);
    }

    @Override
    public void criarExposicao(Exposicao exposicao) throws SQLException {
        ExposicaoDAO edao = new ExposicaoDAO();
        edao.criarExposicao(exposicao);
    }

    @Override
    public boolean adicionarObraEmExposicao(int idObra, int idExpo) throws SQLException {
        ExposicaoDAO edao = new ExposicaoDAO();
        return edao.adicionarObra(idObra, idExpo);
    }

    @Override
    public Vector<Obra> topObras() throws SQLException {
        Vector<Obra> obras = repositorio.listar();

        obras.sort((a,b) -> Double.compare(b.mediaAvaliacoes(), a.mediaAvaliacoes()));

        return obras;
    }

    @Override
    public Vector<Obra> obrasExpostas(String nomeExposicao) throws SQLException, NotaInvalidaException {
        ExposicaoDAO exposicaoDAO = new ExposicaoDAO();
        Exposicao expo = exposicaoDAO.buscarPorNome(nomeExposicao);
        Vector<Obra> obras_expostas = exposicaoDAO.listarObras(expo.getId());
        return obras_expostas;
    }

    @Override
    public void atualizarObra(Obra obraAtualizada) throws SQLException, ObraNaoEncontradaException {
       repositorio.atualizar(obraAtualizada);
    }
}
