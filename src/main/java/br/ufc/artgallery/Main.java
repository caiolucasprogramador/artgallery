package br.ufc.artgallery;

import br.ufc.artgallery.conexao.Conexao;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        try {
            Connection conn = Conexao.getConexao();
            System.out.println("Conexão bem sucedida!");
            conn.close();
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }
}