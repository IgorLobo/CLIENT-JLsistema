package br.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import br.model.Cliente;
import br.persistencia.ClienteDAO;
import br.util.Janela;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;

public class TelaClienteController implements Initializable {
//************************ ATRIBUTOS ********************************
	private Janela utilJanela = new Janela();
	String nomeArquivo = TelaPrincipalController.nomeArquivoCliente;
	private ClienteDAO persistencia = new ClienteDAO(nomeArquivo);
	static String operacao;
	static Cliente clienteSelecionado;
//*********************** COMPONENTES *******************************	

	@FXML
	private Button btn_incluir;

	@FXML
	private Button btn_alterar;

	@FXML
	private Button btn_excluir;

	@FXML
	private Button btn_observacao;

	@FXML
	private TableView<Cliente> tableView_cliente;

	@FXML
	private TableColumn<Cliente, Integer> tc_ID;

	@FXML
	private TableColumn<Cliente, String> tc_nome;

	@FXML
	private TableColumn<Cliente, String> tc_email;

	@FXML
	private TableColumn<Cliente, String> tc_telefone;

//*********************** ON-ACTION *********************************
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		prepararTableView();
		listar();
	}

	@FXML
	void OnClick_btn_alterar(ActionEvent event) {
		try {
			if (tableView_cliente.getSelectionModel().isEmpty()) {

				Alert alert = new Alert(AlertType.INFORMATION);
				alert.setTitle("Atenção");
				alert.setHeaderText(null);
				alert.setContentText("Selecione uma Infraestrutura para Exclusão!");
				alert.show();
				listar();
			} else {
				operacao = "alterar";
				clienteSelecionado = tableView_cliente.getSelectionModel().getSelectedItem();
				utilJanela.novaJanelaComOwnerWait("/br/view/TelaClienteIncluir.fxml", false,
						"Alterar dados do cliente");
				listar();

			}
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void OnClick_btn_excluir(ActionEvent event) {
		try {
			if (tableView_cliente.getSelectionModel().isEmpty()) {
				listar();
			} else {
				persistencia.excluirCliente(tableView_cliente.getSelectionModel().getSelectedItem().getCodCliente());
				listar();
			}
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}

	@FXML
	void OnClick_btn_incluir(ActionEvent event) {
		operacao = "incluir";
		utilJanela.novaJanelaComOwnerWait("/br/view/TelaClienteIncluir.fxml", false, "Cadastrar novo cliente");
		listar();
	}

	@FXML
	void OnClick_btn_observacao(ActionEvent event) {
		listar();
	}

//************************** METODOS AUXILIARES *********************
	private void listar() {
		try {
			ArrayList<Cliente> listaDeClientes;
			listaDeClientes = persistencia.listarClientes();
			tableView_cliente.setItems(FXCollections.observableArrayList(listaDeClientes));
		} catch (Exception erro) {
			JOptionPane.showMessageDialog(null, erro.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
		}
	}

	private void prepararTableView() {
		tc_ID.setCellValueFactory(new PropertyValueFactory<>("codCliente"));
		tc_nome.setCellValueFactory(new PropertyValueFactory<>("nomeCliente"));
		tc_email.setCellValueFactory(new PropertyValueFactory<>("email1Cliente"));
		tc_telefone.setCellValueFactory(new PropertyValueFactory<>("telefone1Cliente"));
	}
}
