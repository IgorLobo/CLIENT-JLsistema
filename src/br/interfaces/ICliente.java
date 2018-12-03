package br.interfaces;

import java.util.ArrayList;

import br.model.Cliente;

public interface ICliente {
	void incluirCliente(Cliente cliente) throws Exception;
    public ArrayList<Cliente> listarClientes() throws Exception;
    public void excluirCliente(int codCliente) throws Exception;
    public void alterarCliente(int codCliente, Cliente cliente) throws Exception;
    public int identity() throws Exception;
}
