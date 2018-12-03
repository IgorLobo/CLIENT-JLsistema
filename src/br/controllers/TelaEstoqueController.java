package br.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import br.model.Produto;
import br.persistencia.ProdutoDAO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaEstoqueController implements Initializable {

//************************ ATRIBUTOS ********************************

//*********************** COMPONENTES *******************************	


	@FXML
	private Button btn_ok;

//TABELA DE VENDAS
	@FXML
	private TitledPane paneVenda;

	@FXML
	private TitledPane paneVendaJogos;

	@FXML
	private TitledPane paneVendaAcessorios;

	@FXML
	private TitledPane paneVendaConsoles;

	@FXML
	private TableView<Produto> venda_tvJogos;

	@FXML
	private TableColumn<Produto, String> venda_tvJogos_tcNome;

	@FXML
	private TableColumn<Produto, String> venda_tvJogos_tcCompatibilidade;

	@FXML
	private TableColumn<Produto, Integer> venda_tvJogos_quantidade;

	@FXML
	private TableView<Produto> venda_tvAcessorios;

	@FXML
	private TableColumn<Produto, String> venda_tvAcessorios_tcNome;

	@FXML
	private TableColumn<Produto, String> venda_tvAcessorios_tcCompatibilidade;

	@FXML
	private TableColumn<Produto, String> venda_tvAcessorios_fabricante;

	@FXML
	private TableColumn<Produto, Integer> venda_tvAcessorios_quantidade;

	@FXML
	private TableView<Produto> venda_tvConsoles;

	@FXML
	private TableColumn<Produto, String> venda_tvConsoles_tcNome;

	@FXML
	private TableColumn<Produto, String> venda_tvConsoles_fabricante;

	@FXML
	private TableColumn<Produto, Integer> venda_tvConsoles_quantidade;

//TABELAS DE LOCAÇÃO

	@FXML
	private TitledPane paneLoc;

	@FXML
	private TitledPane paneLocJogos;

	@FXML
	private TitledPane paneLocAcessorios;

	@FXML
	private TitledPane paneLocConsoles;

	@FXML
	private TableView<Produto> loc_tvJogos;

	@FXML
	private TableColumn<Produto, String> loc_tvJogos_tcNome;

	@FXML
	private TableColumn<Produto, String> loc_tvJogos_tcCompatibilidade;

	@FXML
	private TableColumn<Produto, Integer> loc_tvJogos_quantidade;

	@FXML
	private TableView<Produto> loc_tvAcessorios;

	@FXML
	private TableColumn<Produto, String> loc_tvAcessorios_tcNome;

	@FXML
	private TableColumn<Produto, String> loc_tvAcessorios_tcCompatibilidade;

	@FXML
	private TableColumn<Produto, String> loc_tvAcessorios_fabricante;

	@FXML
	private TableColumn<Produto, Integer> loc_tvAcessorios_quantidade;

	@FXML
	private TableView<Produto> loc_tvConsoles;

	@FXML
	private TableColumn<Produto, String> loc_tvConsoles_tcNome;

	@FXML
	private TableColumn<Produto, String> loc_tvConsoles_fabricante;

	@FXML
	private TableColumn<Produto, Integer> loc_tvConsoles_quantidade;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableViews();
		listar();
	}

	@FXML
	void OnClick_btn_ok(ActionEvent event) {
		br.util.Janela.fecharJanela(btn_ok);
	}

//************************** METODOS AUXILIARES *********************
	private void prepararTableViews() {
		venda_tvJogos_tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		venda_tvJogos_tcCompatibilidade.setCellValueFactory(new PropertyValueFactory<>("compatibilidade"));
		venda_tvJogos_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		venda_tvAcessorios_tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		venda_tvAcessorios_fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
		venda_tvAcessorios_tcCompatibilidade.setCellValueFactory(new PropertyValueFactory<>("compatibilidade"));
		venda_tvAcessorios_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		venda_tvConsoles_tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		venda_tvConsoles_fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
		venda_tvConsoles_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		// TABLE VIEW LOCAÇÃO

		loc_tvJogos_tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		loc_tvJogos_tcCompatibilidade.setCellValueFactory(new PropertyValueFactory<>("compatibilidade"));
		loc_tvJogos_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		loc_tvAcessorios_tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		loc_tvAcessorios_fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
		loc_tvAcessorios_tcCompatibilidade.setCellValueFactory(new PropertyValueFactory<>("compatibilidade"));
		loc_tvAcessorios_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

		loc_tvConsoles_tcNome.setCellValueFactory(new PropertyValueFactory<>("nomeProduto"));
		loc_tvConsoles_fabricante.setCellValueFactory(new PropertyValueFactory<>("fabricante"));
		loc_tvConsoles_quantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

	}

	private void listar() {
		try {
			venda_tvJogos.setItems(FXCollections
					.observableArrayList(new ProdutoDAO(TelaPrincipalController.nomeArquivoJogos).listarProdutos()));
			venda_tvConsoles.setItems(FXCollections
					.observableArrayList(new ProdutoDAO(TelaPrincipalController.nomeArquivoConsoles).listarProdutos()));
			venda_tvAcessorios.setItems(FXCollections.observableArrayList(
					new ProdutoDAO(TelaPrincipalController.nomeArquivoAcessorios).listarProdutos()));

			loc_tvJogos.setItems(FXCollections
					.observableArrayList(new ProdutoDAO(TelaPrincipalController.nomeArquivoJogosLoc).listarProdutos()));
			loc_tvConsoles.setItems(FXCollections.observableArrayList(
					new ProdutoDAO(TelaPrincipalController.nomeArquivoConsolesLoc).listarProdutos()));
			loc_tvAcessorios.setItems(FXCollections.observableArrayList(
					new ProdutoDAO(TelaPrincipalController.nomeArquivoAcessoriosLoc).listarProdutos()));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
