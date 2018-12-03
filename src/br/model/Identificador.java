package br.model;

import br.interfaces.TratamentoDeDados;

public class Identificador implements TratamentoDeDados {
	int id = 0;
	String tipo = "";

	public Identificador(int id, String tipo) {
		id = id;
		this.tipo = tipo;
	}

	public Identificador() {

	}

	@Override
	public void materializar(String dados) throws Exception {
		String vetorString[] = dados.split(";");
		if (vetorString.length != 2)
			throw new Exception("Faltam dados na String");
		this.tipo = vetorString[0];
		this.id = Integer.parseInt(vetorString[1]);
	}

	@Override
	public String desmaterializar() throws Exception {
		String saida = "";
		saida += this.tipo + ";";
		saida += this.id + ";";
		return saida;
	}

	public String desmaterializar(int id) throws Exception {
		this.id = id;
		String saida = "";
		saida += this.tipo + ";";
		saida += this.id + ";";
		return saida;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

}
