package br.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

import br.controllers.TelaPrincipalController;
import br.interfaces.IProduto;
import br.model.Identificador;
import br.model.Produto;

public class ProdutoDAO implements IProduto {
	private String nomeDoArquivo = "";

	public ProdutoDAO(String nomeDoArquivo) {
		this.nomeDoArquivo = nomeDoArquivo;
		Locale.setDefault(Locale.US);
	}

	@Override
	public void incluirProduto(Produto produto) throws Exception {
		try {
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo, true);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			int id = identity();
			// Escreve no arquivo
			bufferedWriter.write(produto.desmaterializar(id) + "\r\n");
			// fecha o arquivo
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
				if (!(identificador.getTipo().equals("Produto"))) {
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
				if (!(identificador1.getTipo().equals("Produto"))) {
					bufferedWriter1.write(identificador1.desmaterializar() + "\r\n");
				}
			}
			id++;
			Identificador i = new Identificador(id, "Produto");
			bufferedWriter1.write(i.desmaterializar(id) + "\r\n");

			bufferedWriter1.close();
			return id;

		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public ArrayList<Produto> listarProdutos() throws Exception {
		try {
			ArrayList<Produto> listaDeProdutos = new ArrayList<Produto>();
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Produto produto = new Produto();
				produto.materializar(linha);
				listaDeProdutos.add(produto);
			}
			bufferedReader.close();
			return listaDeProdutos;
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public void excluirProduto(int codProduto) throws Exception {
		try {
			ArrayList<Produto> listaDeProdutos = this.listarProdutos();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeProdutos.size(); posicao++) {
				Produto produto = listaDeProdutos.get(posicao);
				if (!(produto.getCodProduto() == (codProduto))) {
					bufferedWriter.write(produto.desmaterializar() + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public void alterarProduto(int codProduto, Produto produto) throws Exception {
		try {

			ArrayList<Produto> listaDeClientes = this.listarProdutos();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeClientes.size(); posicao++) {
				Produto produt = listaDeClientes.get(posicao);
				if (!(produt.getCodProduto() == (codProduto))) {
					bufferedWriter.write(produt.desmaterializar() + "\r\n");
				} else {
					int id = codProduto;
					id--;
					produto.setQuantidade(produt.getQuantidade());
					bufferedWriter.write(produto.desmaterializar(id) + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public Produto getProduto(int codProduto) throws Exception {
		try {
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Produto produto = new Produto();
				produto.materializar(linha);
				if (produto.getCodProduto() == codProduto)
					return produto;
			}
			bufferedReader.close();
		} catch (Exception erro) {
			throw erro;
		}

		return null;
	}

	public Produto getProduto(int codProduto, int novoQnt) throws Exception {
		try {
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Produto produto = new Produto();
				produto.materializar(linha);
				if (produto.getCodProduto() == codProduto) {
					produto.definirQuantidade(novoQnt);
					produto.setSubtotal(produto.getValorUnitarioVenda() * produto.getQuantidade());
					return produto;
				}
			}
			bufferedReader.close();
		} catch (Exception erro) {
			throw erro;
		}

		return null;
	}

	public Produto getProdutoLoc(int codProduto, int novoQnt, int diasDesejados) throws Exception {
		try {
			Locale.setDefault(Locale.US);
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Produto produto = new Produto();
				produto.materializar(linha);
				if (produto.getCodProduto() == codProduto) {
					produto.definirQuantidade(novoQnt);
					produto.setSubtotal(Float.parseFloat(String.format("%.2f",
							produto.getValorUnitarioLocacao() * produto.getQuantidade() * diasDesejados)));
					produto.setDias(diasDesejados);
					return produto;
				}
			}
			bufferedReader.close();
		} catch (Exception erro) {
			throw erro;
		}

		return null;
	}

	public void decrementarQuantidade(int codProduto, int qnt) throws Exception {
		try {
			ArrayList<Produto> listaDeClientes = this.listarProdutos();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeClientes.size(); posicao++) {
				Produto produt = listaDeClientes.get(posicao);
				if (!(produt.getCodProduto() == (codProduto))) {
					bufferedWriter.write(produt.desmaterializar() + "\r\n");
				} else {
					produt.decrementarQuantidade(qnt);
					int id = codProduto;
					id--;
					bufferedWriter.write(produt.desmaterializar(id) + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}
	
	public void devolucaoDeProduto(int codProduto, int qnt) throws Exception {
		try {
			ArrayList<Produto> listaDeClientes = this.listarProdutos();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeClientes.size(); posicao++) {
				Produto produt = listaDeClientes.get(posicao);
				if (!(produt.getCodProduto() == (codProduto))) {
					bufferedWriter.write(produt.desmaterializar() + "\r\n");
				} else {
					produt.incrementarQuantidade(qnt);
					int id = codProduto;
					id--;
					bufferedWriter.write(produt.desmaterializar(id) + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}


}
