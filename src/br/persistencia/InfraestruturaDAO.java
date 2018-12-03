package br.persistencia;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Locale;

import br.controllers.TelaPrincipalController;
import br.interfaces.IInfraestrutura;
import br.model.Cliente;
import br.model.Identificador;
import br.model.Infraestrutura;
import br.model.Produto;

public class InfraestruturaDAO implements IInfraestrutura {
	private String nomeDoArquivo = "";

	public InfraestruturaDAO(String nomeDoArquivo) {
		this.nomeDoArquivo = nomeDoArquivo;
		Locale.setDefault(Locale.US);
	}

	@Override
	public void incluirInfraestrutura(Infraestrutura infraestrutura) throws Exception {
		try {
			// cria o arquivo
			FileWriter fileWriter1 = new FileWriter(nomeDoArquivo, true);
			FileWriter fileWriter2 = new FileWriter(TelaPrincipalController.nomeArquivoDataLocInfraestrutura, true);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter1 = new BufferedWriter(fileWriter1);
			BufferedWriter bufferedWriter2 = new BufferedWriter(fileWriter2);
			int id = identity();
			// Escreve no arquivo
			bufferedWriter1.write(infraestrutura.desmaterializar(id) + "\r\n");
			bufferedWriter2.write(infraestrutura.desmaterializarData(id) + "\r\n");
			// fecha o arquivo
			bufferedWriter1.close();
			bufferedWriter2.close();
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
				if (!(identificador.getTipo().equals("Infraestrutura"))) {
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
				if (!(identificador1.getTipo().equals("Infraestrutura"))) {
					bufferedWriter1.write(identificador1.desmaterializar() + "\r\n");
				}
			}
			id++;
			Identificador i = new Identificador(id, "Infraestrutura");
			bufferedWriter1.write(i.desmaterializar(id) + "\r\n");

			bufferedWriter1.close();
			return id;

		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public ArrayList<Infraestrutura> listarInfraestruturas() throws Exception {
		try {
			ArrayList<Infraestrutura> listaDeInfraestruturas = new ArrayList<Infraestrutura>();
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Infraestrutura infraestrutura = new Infraestrutura();
				infraestrutura.materializar(linha);
				listaDeInfraestruturas.add(infraestrutura);
			}
			bufferedReader.close();
			return listaDeInfraestruturas;
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public void excluirInfraestrutura(int codInfraestrutura) throws Exception {
		try {
			ArrayList<Infraestrutura> listaDeInfraestruturas = this.listarInfraestruturas();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeInfraestruturas.size(); posicao++) {
				Infraestrutura infraestrutura = listaDeInfraestruturas.get(posicao);
				if (!(infraestrutura.getCodInfraestrutura() == (codInfraestrutura))) {
					bufferedWriter.write(infraestrutura.desmaterializar() + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	@Override
	public void alterarInfraestrutura(int codInfraestrutura, Infraestrutura infraestrutura) throws Exception {
		try {
			ArrayList<Infraestrutura> listaDeInfraestruturas = this.listarInfraestruturas();
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			for (int posicao = 0; posicao < listaDeInfraestruturas.size(); posicao++) {
				Infraestrutura infra = listaDeInfraestruturas.get(posicao);
				if (!(infra.getCodInfraestrutura() == (codInfraestrutura))) {
					bufferedWriter.write(infra.desmaterializar() + "\r\n");
				} else {
					int id = codInfraestrutura;
					id--;
					bufferedWriter.write(infraestrutura.desmaterializar(id) + "\r\n");
				}
			}
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}

	public Infraestrutura getInfraLoc(int codInfra, String dataLocacao) throws Exception {
		try {
			Locale.setDefault(Locale.US);
			FileReader fileReader = new FileReader(nomeDoArquivo);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String linha = "";
			while ((linha = bufferedReader.readLine()) != null) {
				Infraestrutura infraestrutura = new Infraestrutura();
				infraestrutura.materializar(linha);
				if (infraestrutura.getCodInfraestrutura() == codInfra) {
					infraestrutura.setDataLocacao(dataLocacao);
					return infraestrutura;
				}
			}
			bufferedReader.close();
		} catch (Exception erro) {
			throw erro;
		}
		return null;
	}

	public void incluirDataLocInfraestrutura(Infraestrutura infraestrutura) throws Exception {
		try {
			// cria o arquivo
			FileWriter fileWriter = new FileWriter(nomeDoArquivo, true);
			// Criar o buffer do arquivo
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
			int id = identity();
			// Escreve no arquivo
			bufferedWriter.write(infraestrutura.desmaterializarData(id) + "\r\n");
			// fecha o arquivo
			bufferedWriter.close();
		} catch (Exception erro) {
			throw erro;
		}
	}
}
