package br.interfaces;

import java.util.ArrayList;

import br.model.Produto;


public interface IProduto {
	void incluirProduto(Produto produto) throws Exception;
    public ArrayList<Produto> listarProdutos() throws Exception;
    public void excluirProduto(int codProduto) throws Exception;
    public void alterarProduto(int codProduto, Produto produto) throws Exception;
    public Produto getProduto(int codProduto) throws Exception;
    public int identity() throws Exception;
}
