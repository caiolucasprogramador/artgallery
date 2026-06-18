package br.ufc.artgallery.ui;

import br.ufc.artgallery.ArtGallery;
import br.ufc.artgallery.ui.paineis.*;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private CardLayout cardLayout = new CardLayout();
    private JPanel painelPrincipal = new JPanel(cardLayout);
    private ArtGallery gallery;

    public MainFrame(ArtGallery gallery) {
        this.gallery = gallery;

        setTitle("Galeria de Arte Digital - UFC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(950, 700);
        setLocationRelativeTo(null);

        JPanel painelHome = criarPainelHome();
        PainelCadastro painelCadastro = new PainelCadastro(gallery);
        PainelRemover painelRemover = new PainelRemover(gallery);
        PainelListar painelListar = new PainelListar(gallery);
        PainelBuscarPorAutor painelBuscarAutor = new PainelBuscarPorAutor(gallery);
        PainelBuscarPorTitulo painelBuscarTitulo = new PainelBuscarPorTitulo(gallery);
        PainelAtualizarObra painelAtualizarObra = new PainelAtualizarObra(gallery);
        PainelTopObras painelTopObras = new PainelTopObras(gallery);
        PainelAvaliar painelAvaliar = new PainelAvaliar(gallery);
        PainelAtivarDesativarObra painelStatusObra = new PainelAtivarDesativarObra(gallery);

        PainelObrasExpostas painelObrasExpostas = new PainelObrasExpostas(gallery);
        PainelCriarExposicao painelCriarExpo = new PainelCriarExposicao(gallery);
        PainelAdicionarObraExposicao painelAddObraExpo = new PainelAdicionarObraExposicao(gallery);
        PainelRemoverObraExposicao painelRemoverObraExpo = new PainelRemoverObraExposicao(gallery);
        PainelRemoverExposicao painelRemoverExpo = new PainelRemoverExposicao(gallery);

        painelPrincipal.add(painelHome, "Home");
        painelPrincipal.add(painelCadastro, "CadastroObra");
        painelPrincipal.add(painelRemover, "RemoverObra");
        painelPrincipal.add(painelListar, "ListarObras");
        painelPrincipal.add(painelBuscarAutor, "BuscarAutor");
        painelPrincipal.add(painelBuscarTitulo, "BuscarTitulo");
        painelPrincipal.add(painelAtualizarObra, "AtualizarObra");
        painelPrincipal.add(painelTopObras, "TopObras");
        painelPrincipal.add(painelAvaliar, "AvaliarObra");
        painelPrincipal.add(painelStatusObra, "StatusObra");

        painelPrincipal.add(painelObrasExpostas, "ObrasExpostas");
        painelPrincipal.add(painelCriarExpo, "CriarExposicao");
        painelPrincipal.add(painelAddObraExpo, "AddObraExposicao");
        painelPrincipal.add(painelRemoverObraExpo, "RemoverObraExposicao");
        painelPrincipal.add(painelRemoverExpo, "RemoverExposicao");

        add(painelPrincipal, BorderLayout.CENTER);

        configurarMenu();
    }

    private JPanel criarPainelHome() {
        JPanel painel = new JPanel(new GridBagLayout());
        JLabel lblBoasVindas = new JLabel("Bem-vindo ao Sistema de Gerenciamento da Galeria de Arte", JLabel.CENTER);
        lblBoasVindas.setFont(new Font("Arial", Font.BOLD, 18));
        painel.add(lblBoasVindas);
        return painel;
    }

    private void configurarMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemHome = new JMenuItem("Tela Inicial");
        JMenuItem itemSair = new JMenuItem("Sair");
        menuSistema.add(itemHome);
        menuSistema.add(itemSair);

        JMenu menuObras = new JMenu("Obras de Arte");
        JMenuItem itemCadastrarObra = new JMenuItem("Cadastrar Nova Obra");
        JMenuItem itemRemoverObra = new JMenuItem("Remover Obra do Sistema");
        JMenuItem itemListar = new JMenuItem("Listar Obras Ativas");
        JMenuItem itemBuscarAutor = new JMenuItem("Buscar por Autor");
        JMenuItem itemBuscarTitulo = new JMenuItem("Buscar por Título");
        JMenuItem itemAtualizarObra = new JMenuItem("Atualizar Dados de Obra");
        JMenuItem itemTopObras = new JMenuItem("Ranking Top Obras");
        JMenuItem itemAvaliarObra = new JMenuItem("Avaliar uma Obra");
        JMenuItem itemStatusObra = new JMenuItem("Gerenciar Status (Ativar/Desativar)");

        menuObras.add(itemCadastrarObra);
        menuObras.add(itemRemoverObra);
        menuObras.add(itemAtualizarObra);
        menuObras.add(new JSeparator());
        menuObras.add(itemListar);
        menuObras.add(itemBuscarAutor);
        menuObras.add(itemBuscarTitulo);
        menuObras.add(itemTopObras);
        menuObras.add(itemAvaliarObra);
        menuObras.add(new JSeparator());
        menuObras.add(itemStatusObra);

        JMenu menuExposicoes = new JMenu("Exposições");
        JMenuItem itemVerExpostas = new JMenuItem("Ver Obras Expostas");
        JMenuItem itemCriarExpo = new JMenuItem("Criar Nova Exposição");
        JMenuItem itemAddObraExpo = new JMenuItem("Adicionar Obra na Exposição");
        JMenuItem itemRemoverObraExpo = new JMenuItem("Remover Obra de Exposição");
        JMenuItem itemRemoverExpo = new JMenuItem("Remover Exposição do Sistema");

        menuExposicoes.add(itemVerExpostas);
        menuExposicoes.add(new JSeparator());
        menuExposicoes.add(itemCriarExpo);
        menuExposicoes.add(itemAddObraExpo);
        menuExposicoes.add(itemRemoverObraExpo);
        menuExposicoes.add(itemRemoverExpo);

        itemHome.addActionListener(e -> cardLayout.show(painelPrincipal, "Home"));
        itemSair.addActionListener(e -> System.exit(0));

        itemCadastrarObra.addActionListener(e -> cardLayout.show(painelPrincipal, "CadastroObra"));

        itemRemoverObra.addActionListener(e -> {
            Component comp = obterPainelPorNome("RemoverObra");
            cardLayout.show(painelPrincipal, "RemoverObra");
        });

        itemAtualizarObra.addActionListener(e -> {
            Component comp = obterPainelPorNome("AtualizarObra");
            if (comp instanceof PainelAtualizarObra) {
                ((PainelAtualizarObra) comp).carregarObrasDoBanco();
            }
            cardLayout.show(painelPrincipal, "AtualizarObra");
        });

        itemListar.addActionListener(e -> {
            Component comp = obterPainelPorNome("ListarObras");
            if (comp instanceof PainelListar) ((PainelListar) comp).atualizarTabela();
            cardLayout.show(painelPrincipal, "ListarObras");
        });

        itemBuscarAutor.addActionListener(e -> cardLayout.show(painelPrincipal, "BuscarAutor"));

        itemBuscarTitulo.addActionListener(e -> {
            Component comp = obterPainelPorNome("BuscarTitulo");
            if (comp instanceof PainelBuscarPorTitulo) {
                ((PainelBuscarPorTitulo) comp).limparTela();
            }
            cardLayout.show(painelPrincipal, "BuscarTitulo");
        });

        itemTopObras.addActionListener(e -> {
            Component comp = obterPainelPorNome("TopObras");
            if (comp instanceof PainelTopObras) ((PainelTopObras) comp).atualizarRanking();
            cardLayout.show(painelPrincipal, "TopObras");
        });

        itemAvaliarObra.addActionListener(e -> {
            Component comp = obterPainelPorNome("AvaliarObra");
            if (comp instanceof PainelAvaliar) ((PainelAvaliar) comp).carregarObras();
            cardLayout.show(painelPrincipal, "AvaliarObra");
        });

        itemStatusObra.addActionListener(e -> {
            Component comp = obterPainelPorNome("StatusObra");
            if (comp instanceof PainelAtivarDesativarObra) ((PainelAtivarDesativarObra) comp).atualizarTabela();
            cardLayout.show(painelPrincipal, "StatusObra");
        });

        itemVerExpostas.addActionListener(e -> {
            Component comp = obterPainelPorNome("ObrasExpostas");
            cardLayout.show(painelPrincipal, "ObrasExpostas");
        });

        itemCriarExpo.addActionListener(e -> cardLayout.show(painelPrincipal, "CriarExposicao"));

        itemAddObraExpo.addActionListener(e -> {
            Component comp = obterPainelPorNome("AddObraExposicao");
            if (comp instanceof PainelAdicionarObraExposicao) {
                ((PainelAdicionarObraExposicao) comp).carregarDados();
            }
            cardLayout.show(painelPrincipal, "AddObraExposicao");
        });

        itemRemoverObraExpo.addActionListener(e -> {
            Component comp = obterPainelPorNome("RemoverObraExposicao");
            if (comp instanceof PainelRemoverObraExposicao) {
                ((PainelRemoverObraExposicao) comp).carregarExposicoes();
            }
            cardLayout.show(painelPrincipal, "RemoverObraExposicao");
        });

        itemRemoverExpo.addActionListener(e -> {
            Component comp = obterPainelPorNome("RemoverExposicao");
            if (comp instanceof PainelRemoverExposicao) {
                ((PainelRemoverExposicao) comp).carregarExposicoes();
            }
            cardLayout.show(painelPrincipal, "RemoverExposicao");
        });

        menuBar.add(menuSistema);
        menuBar.add(menuObras);
        menuBar.add(menuExposicoes);

        setJMenuBar(menuBar);
    }

    private Component obterPainelPorNome(String nome) {
        for (int i = 0; i < painelPrincipal.getComponentCount(); i++) {
            Component comp = painelPrincipal.getComponent(i);
            if (comp.getClass().getSimpleName().toLowerCase().contains(nome.toLowerCase())) {
                return comp;
            }
        }
        return null;
    }
}