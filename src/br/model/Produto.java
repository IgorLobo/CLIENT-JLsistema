package br.model;

import br.interfaces.TratamentoDeDados;

public class Produto implements TratamentoDeDados {
	private int codProduto = 0;
	private String tipo = "";
	private String nomeProduto = "";
	private String descricao = "";
	private String fabricante = "";
	private float valorUnitarioVenda = 0;
	private float valorUnitarioLocacao = 0;
	private String compatibilidade = "";
	private int quantidade = 0;
	private float subtotal;
	private int dias = 0;

	public Produto(int codProduto, String tipo, String nomeProduto, String descricao, String fabricante,
			float valorUnitarioVenda, float valorUnitarioLocacao, String compatibilidade, int quantidade) {
		this.codProduto = codProduto;
		this.setTipo(tipo);
		this.nomeProduto = nomeProduto;
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.valorUnitarioVenda = valorUnitarioVenda;
		this.valorUnitarioLocacao = valorUnitarioLocacao;
		this.compatibilidade = compatibilidade;
		this.quantidade = quantidade;
	}

	// venda
	public Produto(String nomeProduto, String tipo, String compatibilidade, float valorUnitarioVenda,
			int quantidade) {
		this.nomeProduto = nomeProduto;
		this.setTipo(tipo);
		this.compatibilidade = compatibilidade;
		this.valorUnitarioVenda = valorUnitarioVenda;
		this.quantidade = quantidade;
	}

	
	  public Produto(int codProduto,String nomeProduto, String tipo, String compatibilidade, float valorUnitarioLocacao, int quantidade, boolean CONSTRUTORLOCACAO) {
	  this.codProduto = codProduto;
	  this.nomeProduto = nomeProduto; 
	  this.setTipo(tipo); 
	  this.compatibilidade = compatibilidade;
	  this.valorUnitarioLocacao = valorUnitarioLocacao;
	  this.quantidade = quantidade; }
	 
	public Produto(int codProduto, String tipo, String nomeProduto, String descricao, String fabricante,
			float valorUnitarioVenda, float valorUnitarioLocacao, String compatibilidade) {
		this.codProduto = codProduto;
		this.setTipo(tipo);
		this.nomeProduto = nomeProduto;
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.valorUnitarioVenda = valorUnitarioVenda;
		this.valorUnitarioLocacao = valorUnitarioLocacao;
		this.compatibilidade = compatibilidade;
	}

	// sem id
	public Produto(String tipo, String nomeProduto, String descricao, String fabricante, String compatibilidade,
			float valorUnitarioVenda, float valorUnitarioLocacao, int quantidade) {
		this.setTipo(tipo);
		this.nomeProduto = nomeProduto;
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.valorUnitarioVenda = valorUnitarioVenda;
		this.valorUnitarioLocacao = valorUnitarioLocacao;
		this.compatibilidade = compatibilidade;
		this.quantidade = quantidade;
	}

	public Produto(Produto objeto, int quantidade) {
		this.codProduto = objeto.getCodProduto();
		this.tipo = objeto.getTipo();
		this.nomeProduto = objeto.getNomeProduto();
		this.descricao = objeto.getDescricao();
		this.fabricante = objeto.getFabricante();
		this.valorUnitarioVenda = objeto.getValorUnitarioVenda();
		this.valorUnitarioLocacao = objeto.getValorUnitarioLocacao();
		this.compatibilidade = objeto.getCompatibilidade();
		this.quantidade = objeto.getQuantidade() + quantidade;
	}

	// sem id e sem qntd
	public Produto(String tipo, String nomeProduto, String descricao, String fabricante, String compatibilidade,
			float valorUnitarioVenda, float valorUnitarioLocacao) {
		this.setTipo(tipo);
		this.nomeProduto = nomeProduto;
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.valorUnitarioVenda = valorUnitarioVenda;
		this.valorUnitarioLocacao = valorUnitarioLocacao;
		this.compatibilidade = compatibilidade;
	}

	public Produto() {

	}

	// -----------------METODOS--------------------
	@Override
	public void materializar(String dados) throws Exception {
		String vetorString[] = dados.split(";");
		if (vetorString.length != 9)
			throw new Exception("Faltam dados na String");
		this.codProduto = Integer.parseInt(vetorString[0]);
		this.tipo = vetorString[1];
		this.nomeProduto = vetorString[2];
		this.descricao = vetorString[3];
		this.fabricante = vetorString[4];
		this.compatibilidade = vetorString[5];
		this.valorUnitarioVenda = Float.parseFloat(vetorString[6]);
		this.valorUnitarioLocacao = Float.parseFloat(vetorString[7]);
		this.quantidade = Integer.parseInt(vetorString[8]);
	}

	@Override
	public String desmaterializar() {
		String saida = "";
		saida += this.codProduto + ";";
		saida += this.tipo + ";";
		saida += this.nomeProduto + ";";
		saida += this.descricao + ";";
		saida += this.fabricante + ";";
		saida += this.compatibilidade + ";";
		saida += String.format("%.2f", this.valorUnitarioVenda) + ";";
		saida += this.valorUnitarioLocacao + ";";
		saida += this.quantidade + ";";
		return saida;
	}

	public String desmaterializar(int id) {
		id++;
		this.codProduto = id;
		String saida = "";
		saida += this.codProduto + ";";
		saida += this.tipo + ";";
		saida += this.nomeProduto + ";";
		saida += this.descricao + ";";
		saida += this.fabricante + ";";
		saida += this.compatibilidade + ";";
		saida += this.valorUnitarioVenda + ";";
		saida += this.valorUnitarioLocacao + ";";
		saida += this.quantidade + ";";
		return saida;
	}

	// -----------------GETS AND SETERS--------------------

	public int getCodProduto() {
		return codProduto;
	}

	public void setCodProduto(int codProduto) {
		this.codProduto = codProduto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public float getValorUnitarioVenda() {
		return valorUnitarioVenda;
	}

	public void setValorUnitarioVenda(float valorUnitarioVenda) {
		this.valorUnitarioVenda = valorUnitarioVenda;
	}

	public float getValorUnitarioLocacao() {
		return valorUnitarioLocacao;
	}

	public void setValorUnitarioLocacao(float valorUnitarioLocacao) {
		this.valorUnitarioLocacao = valorUnitarioLocacao;
	}

	public String getCompatibilidade() {
		return compatibilidade;
	}

	public void setCompatibilidade(String compatibilidade) {
		this.compatibilidade = compatibilidade;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade += quantidade;
	}

	public void definirQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return nomeProduto;
	}

	public float getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(float subtotal) {
		this.subtotal = subtotal;
	}

	public int getDias() {
		return dias;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public void decrementarQuantidade(int qntd) {
		this.quantidade -= qntd;
	}

	public void incrementarQuantidade(int qntd) {
		this.quantidade += qntd;
	}
}