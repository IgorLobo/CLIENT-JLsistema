package br.interfaces;

import java.util.ArrayList;

import br.model.Pedido;

public interface IPedidos {
	void incluirPedido(Pedido pedido) throws Exception;
    public ArrayList<Pedido> listarPedidos() throws Exception;
    public void excluirPedido(int codPedido) throws Exception;
    public void alterarPedido(int codPedido, Pedido pedidoSelecionado) throws Exception;
    public int identity() throws Exception;
}
