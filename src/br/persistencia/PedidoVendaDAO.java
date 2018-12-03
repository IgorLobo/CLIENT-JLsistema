package br.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;

import br.controllers.TelaPrincipalController;
import br.interfaces.IPedidos;
import br.model.Cliente;
import br.model.Identificador;
import br.model.Pedido;

public class PedidoVendaDAO implements IPedidos {
	private String nomeDoArquivo = "";

	public PedidoVendaDAO(String nomeDoArquivo) {
		this.nomeDoArquivo = nomeDoArquivo;
		Locale.setDefault(Locale.US);
	}

	@Override
	public void incluirPedido(Pedido pedido) throws Exception {
		try {
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo, true);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			int id = identity();
			// Escreve no arquivo
			bufferedWriter.write(pedido.desmaterializarVenda(id) + "\r\n");
			// fecha o arquivo
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public ArrayList<Pedido> listarPedidos() throws Exception {
		try {
			ArrayList<Pedido> listaDePedidosVendas = new ArrayList<Pedido>();
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Pedido pedidoVenda = new Pedido();
 				pedidoVenda.materializarPedidoVenda(linha);
				listaDePedidosVendas.add(pedidoVenda);
			}
			bufferedReader.close();
			return listaDePedidosVendas;
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public void excluirPedido(int codPedidoVenda) throws Exception {
		try {
			ArrayList<Pedido> listaDePedidosVenda = this.listarPedidos();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDePedidosVenda.size(); posicao++) {
				Pedido pedidoVenda = listaDePedidosVenda.get(posicao);
				if (!(pedidoVenda.getCodPedido() == (codPedidoVenda))) {
					bufferedWriter.write(pedidoVenda.desmaterializarVenda() + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public void alterarPedido(int codPedidoVenda, Pedido pedidoVenda) throws Exception {
		try {
			ArrayList<Pedido> listaDePedidosVenda = this.listarPedidos();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDePedidosVenda.size(); posicao++) {
				Pedido pedidoVend = listaDePedidosVenda.get(posicao);
				if (!(pedidoVend.getCodPedido() == (codPedidoVenda))) {
					bufferedWriter.write(pedidoVend.desmaterializarVenda() + "\r\n");
				} else {
					int id = codPedidoVenda;
					id--;
					bufferedWriter.write(pedidoVenda.desmaterializarVenda(id) + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public int identity() throws Exception {
		try {
			int id = 0;
			String linhaDoId = "";

			ArrayList<Identificador> listaDeIdentificadores = new ArrayList<Identificador>();
			FileReader fileReader = new FileReader(TelaPrincipalController.ids);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Identificador identificador = new Identificador();
				identificador.materializar(linha);
				listaDeIdentificadores.add(identificador);
			}
			bufferedReader.close();

			FileWriter fileWriter = new FileWriter(TelaPrincipalController.ids);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeIdentificadores.size(); posicao++) {
				Identificador identificador = listaDeIdentificadores.get(posicao);
				if (!(identificador.getTipo().equals("PedidoVenda"))) {
					bufferedWriter.write(identificador.desmaterializar() + "\r\n");
				} else {
					id = identificador.getId();
				}
			}
			bufferedWriter.close();

			FileReader fileReader1 = new FileReader(TelaPrincipalController.ids);
			BufferedReader bufferedReader1 = new BufferedReader(fileReader1);
			ArrayList<Identificador> listaDeIdentificadores1 = new ArrayList<Identificador>();
			while ((linha = bufferedReader1.readLine()) != null) {
				Identificador identificador = new Identificador();
				identificador.materializar(linha);
				listaDeIdentificadores1.add(identificador);
			}
			bufferedReader1.close();

			FileWriter fileWriter1 = new FileWriter(TelaPrincipalController.ids);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			for (int posicao = 0; posicao < listaDeIdentificadores1.size(); posicao++) {
				Identificador identificador1 = listaDeIdentificadores1.get(posicao);
				if (!(identificador1.getTipo().equals("PedidoVenda"))) {
					bufferedWriter1.write(identificador1.desmaterializar() + "\r\n");
				}
			}
			id++;
			Identificador i = new Identificador(id, "PedidoVenda");
			bufferedWriter1.write(i.desmaterializar(id) + "\r\n");

			bufferedWriter1.close();
			return id;

		} catch (Exception erro) {
			throw erro;
		}
	}

}
